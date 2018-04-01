import { Injectable } from '@angular/core';
import {CrudInterface} from './CrudInterface';
import {Observable} from 'rxjs/Observable';
import {User} from '../objects/auditable/User';
import {HttpClient} from '@angular/common/http';
import {BaseService} from './Base.service';
import {ResponseList} from '../objects/ResponseList';
import {Response} from '../objects/Response';

@Injectable()
export class UserService extends BaseService implements CrudInterface<User> {
  // private usersUrl = 'api/v1/users';
  private usersUrl = 'api/users';

  constructor(protected http: HttpClient) {
    super(http);
  }

  getAll(): Observable<User[]> {
    return new Observable((observer) => {
      const subscription = this.http.get<ResponseList>(this.usersUrl).subscribe(
        (rsp: ResponseList) => {
          rsp.data = (rsp.data).map((item: any) => new User(item));
          observer.next(rsp.data);
        }, (rsp: any) => {
          this.handleError(rsp.error);
          observer.error(rsp.error);
        }, () => {
          observer.complete();
          subscription.unsubscribe();
        });
    });
  }

  get(id: number): Observable<User> {
    return new Observable((observer) => {
      const subscription = this.http.get<Response>(this.usersUrl + '/' + id).subscribe(
        (rsp: Response) => {
          observer.next(new User(rsp.data as User));
        }, (rsp: any) => {
          this.handleError(rsp.error);
          observer.error(rsp.error);
        }, () => {
          observer.complete();
          subscription.unsubscribe();
        });
    });
  }

  create(item: User): Observable<User> {
    return new Observable((observer) => {
      const subscription = this.http.put<Response>(this.usersUrl + '/' + item.id, item).subscribe(
        (rsp: Response) => {
          observer.next(new User(rsp.data as User));
        }, (rsp: any) => {
          this.handleError(rsp.error);
          observer.error(rsp.error);
        }, () => {
          observer.complete();
          subscription.unsubscribe();
        });
    });
  }

  delete(id: number): Observable<void> {
    return new Observable((observer) => {
      const subscription = this.http.delete<User>(this.usersUrl + '/' + id).subscribe(
        () => {
          observer.next();
        }, (rsp: any) => {
          this.handleError(rsp.error);
          observer.error(rsp.error);
        }, () => {
          observer.complete();
          subscription.unsubscribe();
        });
    });
  }

  update(item: User): Observable<User> {
    return new Observable((observer) => {
      const subscription = this.http.post<Response>(this.usersUrl + '/' + item.id, item).subscribe(
        (rsp: Response) => {
          observer.next(new User(rsp.data));
        }, (rsp: any) => {
          this.handleError(rsp.error);
          observer.error(rsp.error);
        }, () => {
          observer.complete();
          subscription.unsubscribe();
        });
    });
  }
}
