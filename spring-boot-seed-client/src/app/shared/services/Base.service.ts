import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

declare let toastr: any;

@Injectable()
export class BaseService {
  constructor(protected http: HttpClient) { }

  protected handleError(error: any) {
    if (error != null) {
      if (error.status == null) {
        toastr.error('<b>Connection Lost</b>' +
          '<BR>You have lost connection with the server. Check your network connection' +
          ' and try again.');
      } else {
        toastr.error('<b>Path</b>: ' + error.path +
          '<BR><b>Status</b>: ' + error.status + ' (' + error.error + ')' +
          '<BR><b>Message</b>: ' + error.message);
      }
    }

    return error;
  }
}
