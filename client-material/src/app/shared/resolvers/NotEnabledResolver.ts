import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {AppService} from '../services/app.service';
import {MatSnackBar} from '@angular/material';
import {User} from '../objects/auditable/User';
import {Observable} from 'rxjs/index';

@Injectable()
export class NotEnabledResolver implements Resolve<any> {
  appInfo: any = AppService.APP_INFO;

  constructor(protected appService: AppService, protected router: Router, protected snackBar: MatSnackBar) {}

  protected static HandleError(e: any, msg: any, snackBar: MatSnackBar) {
    console.error('NotEnabledResolver - Error: ', e);
    console.error(msg);
    snackBar.open(msg, 'Resolve Error',
      { duration: 2000 } );
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> {
    return new Observable((observer) => {
      this.appService.getAppInfo().subscribe(
        (appInfo: any) => {
          this.appInfo = appInfo;

          observer.next(appInfo);
        }, (e: any) => {
          NotEnabledResolver.HandleError(e, 'NotEnabledResolver - Unable to get application info', this.snackBar);
          observer.error(e);
          observer.complete();
        }, () => {
          observer.complete();
        }, );
    });
  }
}
