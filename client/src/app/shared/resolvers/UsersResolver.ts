import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {AppService} from '../services/app.service';
import {BaseResolver} from './BaseResolver';
import {UserService} from '../services/user.service';
import {User} from '../objects/auditable/User';

@Injectable()
export class UsersResolver extends BaseResolver implements Resolve<any> {
  constructor(protected router: Router,
              protected appService: AppService,
              protected userService: UserService) {
    super(appService, router);
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> {
    return new Observable((observer) => {
      super.resolve(route, state).subscribe();

      const subscription = this.userService.getAll().subscribe(
        (users: User[]) => {
          observer.next(users);
        }, (e: any) => {
          BaseResolver.HandleError(e, 'UserResolver - Unable to get users');
          this.router.navigate(['/angularerror']);
          observer.complete();
        }, () => {
          subscription.unsubscribe();
          observer.complete();
        }, );
    });
  }
}
