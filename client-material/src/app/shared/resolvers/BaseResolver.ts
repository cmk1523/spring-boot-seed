import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {AppService} from '../services/app.service';
import {MatSnackBar} from '@angular/material';
import {User} from '../objects/auditable/User';
import {Observable} from 'rxjs/index';
import {first} from 'rxjs/operators';

@Injectable()
export class BaseResolver implements Resolve<any> {
  appInfo: any = AppService.APP_INFO;

  constructor(protected appService: AppService, protected router: Router, protected snackBar: MatSnackBar) {}

  protected static HandleError(e: any, msg: any, snackBar: MatSnackBar) {
    console.error('BaseResolver - Error: ', e);
    console.error(msg);
    snackBar.open(msg, 'Resolve Error',
      { duration: 2000 } );
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> {
    return new Observable((observer) => {
      this.appService.getAppInfo().pipe(first()).subscribe(
        (appInfo: any) => {
          this.appInfo = appInfo;

          if (!(this.appInfo.user as User).isEnabled()) {
            this.snackBar.open('Not Enabled', 'Forwarding',
              {duration: 2000});
            this.router.navigate(['/notenabled']);
            observer.complete();
          }

          observer.next(appInfo);
        }, (e: any) => {
          BaseResolver.HandleError(e, 'BaseResolver - Unable to get application info', this.snackBar);
          observer.complete();
        }, () => {
          observer.complete();
        }, );
    });
  }
}
