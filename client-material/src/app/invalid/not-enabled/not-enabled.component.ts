import { Component, OnInit } from '@angular/core';
import {BaseAngularComponent} from '../../shared/components/base-angular/base-angular.component';

@Component({
  selector: 'app-not-enabled',
  templateUrl: './not-enabled.component.html',
  styleUrls: ['./not-enabled.component.css']
})
export class NotEnabledComponent extends BaseAngularComponent implements OnInit {
  constructor() {
    super();
  }

  ngOnInit() {
    super.ngOnInit();
  }

}
