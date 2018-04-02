import { Component, OnInit } from '@angular/core';
import {BaseAngularComponent} from '../../../shared/components/base-angular/base-angular.component';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent extends BaseAngularComponent implements OnInit {

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
