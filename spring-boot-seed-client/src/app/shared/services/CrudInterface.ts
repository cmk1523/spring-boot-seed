import {Observable} from 'rxjs/Observable';

export interface CrudInterface<T> {
  getAll(): Observable<T[]>;
  get(id: number): Observable<T>;
  create(item: T): Observable<T>;
  remove(id: number): Observable<void>;
  delete(id: number): Observable<void>;
  update(item: T): Observable<T>;
}
