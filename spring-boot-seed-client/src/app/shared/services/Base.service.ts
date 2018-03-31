import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

declare let toastr: any;

@Injectable()
export class BaseService {
  constructor(protected http: HttpClient) { }

  protected handleError(error: any) {
    if (error != null) {
      let message = 'Path: ' + error.path +
        '<BR>Status: ' + error.status + ' (' + error.error + ')' +
        '<BR>Message: ' + error.message;
      toastr.error(message);
    }
  }
}
