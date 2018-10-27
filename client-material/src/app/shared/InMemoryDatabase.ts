import {InMemoryDbService, ResponseOptions} from 'angular-in-memory-web-api';
import {User} from './objects/auditable/User';
import {ResponseList} from './objects/ResponseList';
import {Response} from './objects/Response';
import {UtilitiesService} from './services/utilities.service';
import {InMemoryDatabaseBase} from './InMemoryDatabaseBase';

export class InMemoryDatabase extends InMemoryDatabaseBase implements InMemoryDbService {
  public static Users: User[] = [
    new User({
      id: 1,
      name: InMemoryDatabaseBase.TestUser,
      createdBy: InMemoryDatabaseBase.TestUser,
      createdDate: new Date().getTime(),
      updatedBy: InMemoryDatabaseBase.TestUser,
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
    user: InMemoryDatabase.Users[0]
  };

  constructor() {
    super();
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

    if (method === InMemoryDatabaseBase.HTTP_GET) {
      resOptions = this.generateGetResponse(InMemoryDatabase.AppInfo, resOptions);
    }
  }

  handleUsers(resOptions: ResponseOptions, reqInfo: RequestInfo) {
    const url = reqInfo['url'];
    const method = reqInfo['method'];
    const endpoint = '/api/v1/users';

    if (method === InMemoryDatabaseBase.HTTP_PUT) {
      const itemSubmitted = reqInfo['req'].body;
      const itemToCreate: User = this.getUser(InMemoryDatabase.Users.length + 1);

      if (itemToCreate == null) {
        const data: User = new User(itemSubmitted);
        data.id = InMemoryDatabase.Users.length + 1;

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
    } else if (method === InMemoryDatabaseBase.HTTP_DELETE) {
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
    } else if (method === InMemoryDatabaseBase.HTTP_POST) {
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
    } else if (method === InMemoryDatabaseBase.HTTP_GET && this.urlHasParam(url, endpoint)) {
      const id = parseInt(this.getLastRestVariable(url), 10);
      const data: User = this.getUser(id);
      resOptions = this.generateGetResponse(data, resOptions);
    } else if (method === InMemoryDatabaseBase.HTTP_GET && !this.urlHasParam(url, endpoint)) {
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

}
