import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {EventService} from './event.service';

declare let toastr: any;

@Injectable()
export class BaseService {
  constructor(protected http: HttpClient, protected eventService: EventService) { }
  protected baseUrl = '/';
  protected timeout = 10000;

  public static IsDifferent(a: any, b: any) {
    return JSON.stringify(a) !== JSON.stringify(b);
  }

  protected handleError(error: any) {
    this.eventService.loading.next('kill');

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
