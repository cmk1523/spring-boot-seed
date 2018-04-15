import {InMemoryDbService, ResponseOptions} from 'angular-in-memory-web-api';
import {User} from './objects/auditable/User';
import {ResponseList} from './objects/ResponseList';
import {Response} from './objects/Response';
import {UtilitiesService} from './services/utilities.service';

export class InMemoryDatabase implements InMemoryDbService {
  public static TestUser = 'Test User';
  public static HTTP_GET = 'get';
  public static HTTP_PUT = 'put';
  public static HTTP_POST = 'post';
  public static HTTP_DELETE = 'delete';

  public static Users: User[] = [
    new User({
      id: 1,
      name: InMemoryDatabase.TestUser,
      createdBy: InMemoryDatabase.TestUser,
      createdDate: new Date().getTime(),
      updatedBy: InMemoryDatabase.TestUser,
      updatedDate: new Date().getTime(),
      removed: false
    })
  ];

  public static AppInfo: any = {
    name: 'spring-boot-seed',
    title: 'Technical Development Solutions',
    version: '0.1',
    buildNumber: 'inmemory',
    buildDateTime: '04-14-2018 18:00',
    user: {
      username: 'user',
      authorities: [
        {
          authority: 'ROLE_USER'
        }
      ]
    }
  };

  constructor() {
  }

  public static GenerateAppInfoResponse(): Response {
    return new Response( { tool: 1, data: InMemoryDatabase.AppInfo } );
  }

  public static GenerateUsersResponse(): Response {
    return new ResponseList( { tool: 1, data: InMemoryDatabase.Users, size: InMemoryDatabase.Users.length } );
  }

  createDb() {
    return {
      app: [
        { id: 'app' }
      ],
      users: [
        { id: 'users' }
      ]
    };
  }

  responseInterceptor(resOptions: ResponseOptions, reqInfo: RequestInfo) {
    const url = reqInfo['url'];

    // console.log('InMemoryDatabase - responseInterceptor - reqInfo - url: ' + url + ', reqInfo: ', reqInfo);

    if (url.indexOf('/api/v1/users') > -1) {
      console.log('handling users...');
      this.handleUsers(resOptions, reqInfo);
    } else if (url.indexOf('/api/v1/app') > -1) {
      this.handleApp(resOptions, reqInfo);
    } else {
      console.error('InMemoryDatabase - responseInterceptor - Unknown endpoint: ' + url);
    }

    return resOptions;
  }

  handleApp(resOptions: ResponseOptions, reqInfo: RequestInfo) {
    const url = reqInfo['url'];
    const method = reqInfo['method'];
    const endpoint = '/api/v1/app';

    if (method === InMemoryDatabase.HTTP_GET) {
      resOptions = this.generateGetResponse(InMemoryDatabase.AppInfo, resOptions);
    }
  }

  handleUsers(resOptions: ResponseOptions, reqInfo: RequestInfo) {
    const url = reqInfo['url'];
    const method = reqInfo['method'];
    const endpoint = '/api/v1/users';

    if (method === InMemoryDatabase.HTTP_PUT) {
      const itemSubmitted = reqInfo['req'].body;
      const itemToCreate: User = this.getUser(InMemoryDatabase.Users.length + 1);

      if (itemToCreate == null) {
        const data: User = new User({
          id: InMemoryDatabase.Users.length + 1,
          name: itemSubmitted.name,
          createdBy: itemSubmitted.createdBy,
          createdDate: itemSubmitted.createdDate,
          updatedBy: itemSubmitted.updatedBy,
          updatedDate: itemSubmitted.updatedDate,
          removed: itemSubmitted.removed
        });

        const vr = User.Validate(data, true);

        if (!vr.isValid) {
          console.error('InMemoryDatabase - responseInterceptor - creating user failed: ' + vr.message);
        } else {
          InMemoryDatabase.Users.push(data);
        }

        resOptions = this.generateCreateResponse(data, resOptions);
      } else {
        this.generateItemAlreadyExistsResponse(resOptions);
      }
    } else if (method === InMemoryDatabase.HTTP_DELETE) {
      const id = parseInt(this.getLastRestVariable(url), 10);
      const deleteParam = UtilitiesService.GetUrlParam(url, 'delete');

      if (deleteParam != null && deleteParam !== '' && (deleteParam === true || deleteParam === 'true')) {
        if (id != null) {
          InMemoryDatabase.Users = this.getUsers().filter((i: User) => i.id !== id);
          resOptions = this.generateDeleteResponse(resOptions);
        } else {
          this.generateUnableToFindItemResponse(id, resOptions);
        }
      } else {
        if (id != null) {
          const data: User = this.getUser(id);
          data.removed = true;
          resOptions = this.generateDeleteResponse(resOptions);
        } else {
          this.generateUnableToFindItemResponse(id, resOptions);
        }
      }
    } else if (method === InMemoryDatabase.HTTP_POST) {
      const itemSubmitted = reqInfo['req'].body;
      const id = parseInt(itemSubmitted.id, 10);
      const data: User = this.getUser(id);

      if (data != null) {
        data.name = itemSubmitted.name;
        data.updatedBy = itemSubmitted.updatedBy;
        data.updatedDate = itemSubmitted.updatedDate;
        data.removed = itemSubmitted.removed;
        resOptions = this.generateGetResponse(data, resOptions);
      } else {
        this.generateUnableToFindItemResponse(itemSubmitted.id, resOptions);
      }
    } else if (method === InMemoryDatabase.HTTP_GET && this.urlHasParam(url, endpoint)) {
      const id = parseInt(this.getLastRestVariable(url));
      const data: User = this.getUser(id);
      resOptions = this.generateGetResponse(data, resOptions);
    } else if (method === InMemoryDatabase.HTTP_GET && !this.urlHasParam(url, endpoint)) {
      resOptions = this.generateGetResponseList(this.getUsers(), resOptions);
    }
  }

  getUsers(): User[] {
    return InMemoryDatabase.Users;
  }

  getUser(id: number): User {
    const items: User[] = InMemoryDatabase.Users.filter((i: User) => i.id === id);

    if (items != null && items.length > 0) {
      return items[0];
    } else {
      return null;
    }
  }

  urlHasParam(url: string, endpoint: string): boolean {
    return url.indexOf(endpoint + '/') > -1;
  }

  generateUnableToFindItemResponse(id: any, resOptions: ResponseOptions) {
    resOptions.body = null;
    resOptions['error'] = {
      status: 500,
      error: 'InMemoryDatabase Error',
      message: 'Unable to find item: ' + id
    };
    resOptions.status = 500;
  }

  generateItemAlreadyExistsResponse(resOptions: ResponseOptions) {
    resOptions.body = null;
    resOptions['error'] = {
      status: 500,
      error: 'InMemoryDatabase Error',
      message: 'Itme already exists'
    };
    resOptions.status = 500;
  }

  generateGetResponseList(data: any, resOptions: ResponseOptions) {
    resOptions.body = new ResponseList( { took: 1, data: data, size: data.size } );
    resOptions.status = 200;
    resOptions.statusText = '';
    return resOptions;
  }

  generateGetResponse(data: any, resOptions: ResponseOptions) {
    resOptions.body = new Response({ took: 1, data: data } );
    resOptions.status = 200;
    resOptions.statusText = '';
    return resOptions;
  }

  generateCreateResponse(data: any, resOptions: ResponseOptions) {
    resOptions.body = new Response({ took: 1, data: data } );
    resOptions.status = 201;
    resOptions.statusText = '';
    return resOptions;
  }

  generateDeleteResponse(resOptions: ResponseOptions) {
    resOptions.body = new Response({ took: 1, data: null } );
    resOptions.status = 202;
    resOptions.statusText = '';
    return resOptions;
  }

  getLastRestVariable(s: string) {
    const split = s.split('/');
    return split[split.length - 1];
  }

}
