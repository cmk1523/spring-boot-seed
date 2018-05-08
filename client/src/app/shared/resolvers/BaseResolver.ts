import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {AppService} from '../services/app.service';

declare let toastr: any;

@Injectable()
export class BaseResolver implements Resolve<any> {
  appInfo: any = AppService.APP_INFO;

  constructor(protected appService: AppService, protected router: Router) {}

  protected static HandleError(e, msg: any) {
    console.error(e);
    console.error(msg);
    toastr.error(msg);
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> {
    return new Observable((observer) => {
      this.appService.getAppInfo().subscribe(
        (appInfo: any) => {
          observer.next(appInfo);
        }, (e: any) => {
          BaseResolver.HandleError(e, 'BaseResolver - Unable to get application info');
          observer.complete();
        }, () => {
          observer.complete();
        }, );
    });
  }
}
