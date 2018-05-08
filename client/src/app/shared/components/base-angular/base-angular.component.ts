import { Component, OnInit } from '@angular/core';
import {AppService} from '../../services/app.service';

@Component({
  selector: 'app-base-angular',
  templateUrl: './base-angular.component.html',
  styleUrls: ['./base-angular.component.css']
})
export class BaseAngularComponent implements OnInit {
  appInfo: any = AppService.APP_INFO;

  constructor() { }

  ngOnInit() {
  }

}
