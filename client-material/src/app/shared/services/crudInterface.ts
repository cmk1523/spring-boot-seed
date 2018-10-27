import {Search} from '../objects/Search';
import {Observable} from 'rxjs/index';

export interface CrudInterface<T> {
  search(search: Search): Observable<T[]>;
  getAll(): Observable<T[]>;
  get(id: number): Observable<T>;
  create(item: T): Observable<T>;
  remove(id: number): Observable<void>;
  delete(id: number): Observable<void>;
  update(item: T): Observable<T>;
}
