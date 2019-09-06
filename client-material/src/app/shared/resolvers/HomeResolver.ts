import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {AppService} from '../services/app.service';
import {BaseResolver} from './BaseResolver';
import {MatSnackBar} from '@angular/material';
import {User} from '../objects/auditable/User';
import {Observable} from 'rxjs/index';
import {first} from 'rxjs/operators';

@Injectable()
export class HomeResolver extends BaseResolver implements Resolve<any> {
  constructor(protected router: Router,
              protected appService: AppService,
              protected snackBar: MatSnackBar) {
    super(appService, router, snackBar);
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> {
    return new Observable((observer) => {
      super.resolve(route, state).pipe(first()).subscribe(() => {
        if ((this.appInfo.user as User).isAdmin()) {
          console.log('goto admin...')
          // this.router.navigate(['/admin']);
          observer.complete();
        } else {
          this.router.navigate(['/home']);
          observer.complete();
        }
      }, (e: any) => {
        observer.complete();
      }, () => {
        observer.complete();
      });
    });
  }
}
