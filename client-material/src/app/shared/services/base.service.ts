import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {EventService} from './event.service';
import {MatSnackBar} from '@angular/material';

@Injectable()
export class BaseService {
  protected baseUrl = '/';
  protected timeout = 10000;
  protected snackBarTimeout = 2000;

  public static IsDifferent(a: any, b: any) {
    return JSON.stringify(a) !== JSON.stringify(b);
  }

  constructor(protected http: HttpClient, protected eventService: EventService, protected snackBar: MatSnackBar) {
  }

  protected handleError(error: any) {
    this.eventService.loading.next('kill');

    if (error != null) {
      if (error.status == null) {
        this.snackBar.open('Connection Lost', 'HTTP Error',
          { duration: this.snackBarTimeout } );
      } else {
        this.snackBar.open('Path:' + error.path, 'HTTP Error',
          { duration: this.snackBarTimeout } );
        this.snackBar.open('Status:' + error.error.status + ' (' + error.error + ')', 'HTTP Error',
          { duration: this.snackBarTimeout } );
        this.snackBar.open('Message:' + error.message, 'HTTP Error',
          { duration: this.snackBarTimeout } );
      }
    }

    return error;
  }
}
