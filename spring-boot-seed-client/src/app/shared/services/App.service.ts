import { Injectable } from '@angular/core';
import {BaseService} from './Base.service';
import {Observable} from 'rxjs/Observable';
import {Response} from '../objects/Response';
import {HttpClient} from '@angular/common/http';
import {ReplaySubject} from 'rxjs/ReplaySubject';

@Injectable()
export class AppService extends BaseService {
  // public static APP_INFO = {};
  // private appUrl = 'api/v1/app';
  private appUrl = 'api/app';
  public appInfo: ReplaySubject<any> = new ReplaySubject<any>();

  constructor(protected http: HttpClient) {
    super(http);
  }

  getAppInfo(): Observable<any> {
    return new Observable((observer) => {
      // if (Object.keys(AppService.APP_INFO).length === 0) {
        const subscription = this.http.get<Response>(this.appUrl + '/info').subscribe(
          (rsp: Response) => {
            this.appInfo.next(rsp.data);
            // AppService.APP_INFO = rsp.data;
            observer.next(null);
          }, (rsp: any) => {
            this.handleError(rsp.error);
            observer.error(rsp.error);
          }, () => {
            observer.complete();
            subscription.unsubscribe();
          });
      // } else {
      //   observer.next(AppService.APP_INFO);
      //   observer.complete();
      // }
    });
  }
}