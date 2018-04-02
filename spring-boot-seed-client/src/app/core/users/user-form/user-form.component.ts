import { Component, OnInit } from '@angular/core';
import {BaseAngularComponent} from '../../../shared/components/base-angular/base-angular.component';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent extends BaseAngularComponent implements OnInit {

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
