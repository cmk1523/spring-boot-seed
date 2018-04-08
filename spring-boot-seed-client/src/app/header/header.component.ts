import { Component, OnInit } from '@angular/core';
import {AppService} from '../shared/services/App.service';
import {BaseAngularComponent} from '../shared/components/base-angular/base-angular.component';

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
  }

}
