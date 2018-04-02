import { Component, OnInit } from '@angular/core';
import {AppService} from '../shared/services/App.service';
import {BaseAngularComponent} from '../shared/components/base-angular/base-angular.component';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent extends BaseAngularComponent implements OnInit {
  constructor() {
    super();
  }

  ngOnInit() {
  }

}
