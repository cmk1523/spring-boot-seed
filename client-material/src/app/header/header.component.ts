import { Component, OnInit } from '@angular/core';
import {BaseAngularComponent} from '../shared/components/base-angular/base-angular.component';
import {User} from '../shared/objects/auditable/User';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent extends BaseAngularComponent implements OnInit {
  constructor() {
    super();
  }

  ngOnInit() {
    super.ngOnInit();
  }

}
