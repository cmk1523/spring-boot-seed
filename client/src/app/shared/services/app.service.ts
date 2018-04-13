import { Injectable } from '@angular/core';
import {BaseService} from './base.service';
import {Observable} from 'rxjs/Observable';
import {Response} from '../objects/Response';
import {HttpClient} from '@angular/common/http';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {EventService} from './event.service';

@Injectable()
export class AppService extends BaseService {
  public static APP_INFO: any = null;
  protected appUrl = this.baseUrl + 'api/v1/app';
  public appInfo: ReplaySubject<any> = new ReplaySubject<any>();

  constructor(protected http: HttpClient, protected eventService: EventService) {
    super(http, eventService);
  }

  getAppInfo(): Observable<any> {
    return new Observable((observer) => {
      if (!AppService.APP_INFO) {
        this.eventService.loading.next(true);

        const subscription = this.http.get<Response>(this.appUrl)
          .timeout(this.timeout)
          .subscribe(
          (rsp: Response) => {
            this.appInfo.next(rsp.data);
            AppService.APP_INFO = rsp.data;
            observer.next(AppService.APP_INFO);
          }, (rsp: any) => {
            observer.error(this.handleError(rsp.error));
          }, () => {
            this.eventService.loading.next(false);
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
