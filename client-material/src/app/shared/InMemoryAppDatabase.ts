import {ResponseOptions} from 'angular-in-memory-web-api';
import {InMemoryDatabaseBase} from './InMemoryDatabaseBase';

export class InMemoryAppDatabase extends InMemoryDatabaseBase {
  public static AppInfo: any = {
    name: 'spring-boot-seed-client',
    title: 'Spring Boot Seed Client',
    version: '0.1',
    buildNumber: 'inmemory',
    buildDateTime: '04-14-2018 18:00',
    user: {},
    settings: {
      user: {
        name: 'Test User'
      },
      company: {
        name: 'Test Company Name'
      }
    }
  };

  constructor() {
    super();
  }

  handleApp(resOptions: ResponseOptions, reqInfo: RequestInfo, appInfo: any) {
    const url = reqInfo['url'];
    const method = reqInfo['method'];
    const endpoint = '/api/v1/app/';

    if (method === InMemoryDatabaseBase.HTTP_POST && this.urlHasParam(url, endpoint + 'settings')) {
      this.updateSettings(url, resOptions, reqInfo, appInfo);
    } else if (method === InMemoryDatabaseBase.HTTP_GET) {
      resOptions = this.generateGetResponse(InMemoryAppDatabase.AppInfo, resOptions);
    }
  }

  updateSettings(url: string, resOptions: ResponseOptions, reqInfo: RequestInfo, appInfo) {
    const itemSubmitted = reqInfo['req'].body;

    if (itemSubmitted != null) {
      if (this.hasAdminAccess(appInfo.user)) {
        InMemoryAppDatabase.AppInfo.settings.supplementalRate = itemSubmitted.supplementalRate;
        InMemoryAppDatabase.AppInfo.settings.fieldServicesPremium = itemSubmitted.fieldServicesPremium;
        InMemoryAppDatabase.AppInfo.settings.specialProjectPremium = itemSubmitted.specialProjectPremium;
        InMemoryAppDatabase.AppInfo.settings.lodgingWmaRate = itemSubmitted.lodgingWmaRate;
        InMemoryAppDatabase.AppInfo.settings.lodgingNonWmaRate = itemSubmitted.lodgingNonWmaRate;
        InMemoryAppDatabase.AppInfo.settings.mie_breakfast = itemSubmitted.mie_breakfast;
        InMemoryAppDatabase.AppInfo.settings.mie_lunch = itemSubmitted.mie_lunch;
        InMemoryAppDatabase.AppInfo.settings.mie_dinner = itemSubmitted.mie_dinner;
        InMemoryAppDatabase.AppInfo.settings.mie_incidentals = itemSubmitted.mie_incidentals;
        InMemoryAppDatabase.AppInfo.settings.localTransportation = itemSubmitted.localTransportation;
        resOptions = this.generateGetResponse(InMemoryAppDatabase.AppInfo, resOptions);
      } else {
        this.generateItemForbiddenResponse(resOptions);
      }
    } else {
      this.generateInvalidInputResponse(itemSubmitted, resOptions);
    }
  }

}
