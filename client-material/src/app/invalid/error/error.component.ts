import { Component, OnInit } from '@angular/core';
import {BaseAngularComponent} from '../../shared/components/base-angular/base-angular.component';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss']
})
export class ErrorComponent extends BaseAngularComponent implements OnInit {
  constructor() {
    super();
  }

  ngOnInit() {
    super.ngOnInit();
  }

}
