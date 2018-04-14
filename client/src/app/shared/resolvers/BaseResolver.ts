import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {AppService} from '../services/App.service';

declare let toastr: any;

@Injectable()
export class BaseResolver implements Resolve<any> {
  constructor(protected appService: AppService, protected router: Router) {}

  protected static HandleError(msg: any) {
    console.error(msg);
    toastr.error(msg);
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> {
    return new Observable((observer) => {
      this.appService.getAppInfo().subscribe(
        (rsp: any) => {
          observer.next(null);
        }, (e: any) => {
          BaseResolver.HandleError('BaseResolver - Unable to get application info');
          observer.error(e);
        }, () => {
          observer.complete();
        }, );
    });
  }
}
