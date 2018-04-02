import { Component, OnInit } from '@angular/core';
import {AppService} from '../shared/services/App.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {
  appInfo: any = AppService.APP_INFO;

  constructor() { }

  ngOnInit() {
  }

}
