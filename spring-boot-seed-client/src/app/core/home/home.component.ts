import { Component, OnInit } from '@angular/core';
import {UserService} from '../../shared/services/User.service';
import {AppService} from '../../shared/services/App.service';
import {User} from '../../shared/objects/auditable/User';
import {BaseAngularComponent} from '../../shared/components/base-angular/base-angular.component';

declare let toastr: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent extends BaseAngularComponent implements OnInit {
  constructor() {
    super();
  }

  ngOnInit() {

  }

}
