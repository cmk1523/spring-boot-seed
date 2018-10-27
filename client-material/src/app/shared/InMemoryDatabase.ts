import {InMemoryDbService, ResponseOptions} from 'angular-in-memory-web-api';
import {Response} from './objects/Response';
import {InMemoryDatabaseBase} from './InMemoryDatabaseBase';
import {InMemoryAppDatabase} from './InMemoryAppDatabase';

export class InMemoryDatabase extends InMemoryDatabaseBase implements InMemoryDbService {
  appDb: InMemoryAppDatabase = new InMemoryAppDatabase();

  constructor() {
    super();
  }

  public static GenerateAppInfoResponse(): Response {
    return new Response( { took: 1, data: InMemoryAppDatabase.AppInfo } );
  }

  createDb() {
    return {
      app: [
        { id: 'app' }
      ]
    };
  }

  responseInterceptor(resOptions: ResponseOptions, reqInfo: RequestInfo) {
    const url = reqInfo['url'];

    console.log('InMemoryDatabase - responseInterceptor - reqInfo - url: ' + url);

     if (url.indexOf('/api/v1/app') > -1) {
      this.appDb.handleApp(resOptions, reqInfo, InMemoryAppDatabase.AppInfo);
    } else {
      console.error('InMemoryDatabase - responseInterceptor - Unknown endpoint: ' + url);
    }

    return resOptions;
  }
}
