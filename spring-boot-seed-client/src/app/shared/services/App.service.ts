import { Injectable } from '@angular/core';
import {BaseService} from './Base.service';
import {Observable} from 'rxjs/Observable';
import {Response} from '../objects/Response';
import {HttpClient} from '@angular/common/http';
import {ReplaySubject} from 'rxjs/ReplaySubject';

@Injectable()
export class AppService extends BaseService {
  private appUrl = 'api/v1/app';
  public static APP_INFO: any = null;
  public appInfo: ReplaySubject<any> = new ReplaySubject<any>();

  constructor(protected http: HttpClient) {
    super(http);
  }

  getAppInfo(): Observable<any> {
    return new Observable((observer) => {
      if (!AppService.APP_INFO) {
        const subscription = this.http.get<Response>(this.appUrl).subscribe(
          (rsp: Response) => {
            this.appInfo.next(rsp.data);
            AppService.APP_INFO = rsp.data;
            observer.next(AppService.APP_INFO);
          }, (rsp: any) => {
            observer.error(this.handleError(rsp.error));
          }, () => {
            observer.complete();
            subscription.unsubscribe();
          });
      } else {
        observer.next(AppService.APP_INFO);
        observer.complete();
      }
    });
  }
}
