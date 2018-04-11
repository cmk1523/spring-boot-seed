import {InMemoryDbService, ResponseOptions} from 'angular-in-memory-web-api';
import {User} from './objects/auditable/User';
import {ResponseList} from './objects/ResponseList';
import {Response} from './objects/Response';
import {UtilitiesService} from './services/utilities.service';

export class InMemoryDatabase implements InMemoryDbService {
  public static APP_INFO: Response = new Response();
  public static Users: User[] = [];

  public static APP_INFO_TEST: Response = InMemoryDatabase.GenerateAppInfo();
  public static APP_USERS_TEST: Response = InMemoryDatabase.GenerateDefaultUsers();

  constructor() {
    InMemoryDatabase.APP_INFO = InMemoryDatabase.GenerateAppInfo();
  }

  public static GenerateAppInfo(): Response {
    const response: Response = new Response();
    response.took = 1;
    response.data = {
      name: 'spring-boot-seed',
      title: 'Technical Development Solutions'
      version: '0.1',
      buildNumber: 'in-memory',
      buildDateTime: '01-01-2018 00:00',
      user: {
        username: 'user',
        authorities: [
          {
            authority: 'ROLE_USER'
          }
        ]
      }
    };
    return response;
  }

  public static GenerateDefaultUsers(): ResponseList {
    const testUser = 'Test User';
    const user: User = new User();
    user.id = 1;
    user.name = testUser;
    user.createdBy = testUser;
    user.createdDate = new Date().getTime();
    user.updatedBy = user.createdBy;
    user.updatedDate = user.createdDate;
    user.removed = false;

    InMemoryDatabase.Users.push(user);
    return InMemoryDatabase.GenerateUsersResponse();
  }

  public static GenerateUsersResponse(): ResponseList {
    const responseList: ResponseList = new ResponseList();
    responseList.size = InMemoryDatabase.Users.length;
    responseList.data = InMemoryDatabase.Users;
    responseList.took = 1;
    return responseList;
  }

  responseInterceptor(resOptions: ResponseOptions, reqInfo: RequestInfo) {
    const url = reqInfo['url'];

    // console.log('InMemoryDatabase - responseInterceptor - reqInfo - url: ' + url + ', reqInfo: ', reqInfo);

    if (url.indexOf('api/v1/users') > -1) {
      if (reqInfo['method'] === 'put' && url === 'api/v1/users') { // PUT (Create)
        const itemSubmitted = reqInfo['req'].body;
        const itemToCreate: User = InMemoryDatabase.Users.filter((i: User) => i.id === parseInt(itemSubmitted.id, 10))[0];

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
      } else if (reqInfo['method'] === 'delete' && url.indexOf('api/v1/users/') > -1) {
        const id = this.getLastRestVariable(url);
        const deleteParam = UtilitiesService.GetUrlParam(url, 'delete');

        if (deleteParam != null && deleteParam !== '' && (deleteParam === true || deleteParam === 'true')) {
          if (id != null) {
            InMemoryDatabase.Users = InMemoryDatabase.Users.filter((i: User) => i.id !== parseInt(id, 10));
            resOptions = this.generateDeleteResponse(resOptions);
          } else {
            this.generateUnableToFindItemResponse(id, resOptions);
          }
        } else {
          if (id != null) {
            const data: User = InMemoryDatabase.Users.filter((i: User) => i.id === parseInt(id, 10) && i.removed === false)[0];
            data.removed = true;
            resOptions = this.generateDeleteResponse(resOptions);
          } else {
            this.generateUnableToFindItemResponse(id, resOptions);
          }
        }
      } else if (reqInfo['method'] === 'post' && url.indexOf('api/v1/users/') > -1) { // POST (Update)
        const itemSubmitted = reqInfo['req'].body;
        const id = parseInt(itemSubmitted.id, 10);
        const itemToUpdate: User = InMemoryDatabase.Users.filter((i: User) => i.id === id && i.removed === false)[0];

        if (itemToUpdate != null) {
          itemToUpdate.name = itemSubmitted.name;
          itemToUpdate.updatedBy = itemSubmitted.updatedBy;
          itemToUpdate.updatedDate = itemSubmitted.updatedDate;
          itemToUpdate.removed = itemSubmitted.removed;
          resOptions = this.generateGetResponse(itemToUpdate, resOptions);
        } else {
          this.generateUnableToFindItemResponse(itemSubmitted.id, resOptions);
        }
      } else if (url.indexOf('api/v1/users/') > -1) { // GET (Get via ID)
        const id = this.getLastRestVariable(url);
        const data: User = InMemoryDatabase.Users.filter((i: User) => i.id === parseInt(id, 10) && i.removed === false)[0];
        resOptions = this.generateGetResponse(data, resOptions);
      } else if (url === 'api/v1/users') { // GET (Get All)
        resOptions.body = InMemoryDatabase.GenerateUsersResponse();
      }
    } else if (url.indexOf('api/v1/app') > -1) {
      if (url === 'api/v1/app') {
        resOptions.body = InMemoryDatabase.APP_INFO;
      }
    } else {
      console.error('InMemoryDatabase - responseInterceptor - Unknown endpoint: ' + url);
    }

    return resOptions;
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

  generateGetResponse(data: any, resOptions: ResponseOptions) {
    const response: Response = new Response();
    response.data = data;
    response.took = 1;

    resOptions.body = response;
    resOptions.status = 200;
    resOptions.statusText = '';
    return resOptions;
  }

  generateCreateResponse(data: any, resOptions: ResponseOptions) {
    const response: Response = new Response();
    response.data = data;
    response.took = 1;

    resOptions.body = response;
    resOptions.status = 201;
    resOptions.statusText = '';
    return resOptions;
  }

  generateDeleteResponse(resOptions: ResponseOptions) {
    const response: Response = new Response();
    response.data = null;
    response.took = 1;

    resOptions.body = response;
    resOptions.status = 202;
    resOptions.statusText = '';
    return resOptions;
  }

  getLastRestVariable(s: string) {
    const split = s.split('/');
    return split[split.length - 1];
  }

}
