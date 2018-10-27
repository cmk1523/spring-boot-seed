import { Injectable } from '@angular/core';
import {BaseService} from './base.service';
import {Response} from '../objects/Response';
import {HttpClient} from '@angular/common/http';
import {EventService} from './event.service';
import {MatSnackBar} from '@angular/material';
import {User} from '../objects/auditable/User';
import {Company} from '../objects/auditable/Company';
import {timeout} from 'rxjs/internal/operators';
import {Observable, ReplaySubject} from 'rxjs/index';

@Injectable()
export class AppService extends BaseService {
  public static APP_INFO: any = null;
  protected appUrl = this.baseUrl + 'api/v1/app';
  public appInfo: ReplaySubject<any> = new ReplaySubject<any>();

  constructor(protected http: HttpClient, protected eventService: EventService, protected snackBar: MatSnackBar) {
    super(http, eventService, snackBar);
  }

  getAppInfo(): Observable<any> {
    return new Observable((observer) => {
      if (!AppService.APP_INFO) {
        this.eventService.loading.next(true);

        const subscription = this.http.get<Response>(this.appUrl)
          .pipe(
            timeout(this.timeout)
          )
          .subscribe(
          (rsp: Response) => {
            rsp.data.user = new User(rsp.data.user);
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

  saveSettings(settings: any): Observable<any> {
    return new Observable((observer) => {
      this.eventService.loading.next(true);

      const subscription = this.http.post<Response>(this.appUrl + '/settings', settings)
        .pipe(
          timeout(this.timeout)
        )
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
    });
  }
}
