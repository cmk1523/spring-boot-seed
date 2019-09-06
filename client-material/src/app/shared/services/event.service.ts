import { Injectable } from '@angular/core';
import {Subject} from 'rxjs/index';

@Injectable()
export class EventService {
  public loading: Subject<any> = new Subject<any>();

  constructor() { }

}
