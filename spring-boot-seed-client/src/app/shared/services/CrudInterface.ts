import {Observable} from 'rxjs/Observable';
import {User} from '../objects/auditable/User';

export interface CrudInterface<T> {
  getAll(): Observable<T[]>;
  get(id: number): Observable<T>;
  create(item: T): Observable<T>;
  delete(id: number): Observable<void>;
  update(item: T): Observable<T>;
}
