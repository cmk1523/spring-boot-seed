import { Component, OnInit } from '@angular/core';
import {AppService} from '../../services/app.service';
import {User} from '../../objects/auditable/User';

@Component({
  selector: 'app-base-angular',
  templateUrl: './base-angular.component.html',
  styleUrls: ['./base-angular.component.css']
})
export class BaseAngularComponent implements OnInit {
  appInfo: any = AppService.APP_INFO;
  isAdmin = false;
  snackBarTimeout = 2000;
  showBreadcrumbs = true;
  REQUIRED = 'required';
  BASIC_EMPTY_VALUE_MSG = 'You must enter a value';

  constructor() { }

  ngOnInit() {
    this.isAdmin = (this.appInfo) ? (this.appInfo.user as User).isAdmin() : false;
  }

}
