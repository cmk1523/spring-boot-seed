import {InMemoryDbService, ResponseOptions} from 'angular-in-memory-web-api';
import {User} from './objects/auditable/User';
import {ResponseList} from './objects/ResponseList';
import {Response} from './objects/Response';
import {UtilitiesService} from './services/utilities.service';
import {Role} from './objects/auditable/Role';

export class InMemoryDatabaseBase {
  public static TestUser = 'Test User';
  public static HTTP_GET = 'get';
  public static HTTP_PUT = 'put';
  public static HTTP_POST = 'post';
  public static HTTP_DELETE = 'delete';

  constructor() {
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
