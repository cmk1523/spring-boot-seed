import {Component, Input, OnInit} from '@angular/core';

import * as moment from 'moment';

@Component({
  selector: 'app-millisecond-to-date',
  templateUrl: './millisecond-to-date.component.html',
  styleUrls: ['./millisecond-to-date.component.css']
})
export class MillisecondToDateComponent implements OnInit {
  public static MMMM_DD_YYYY = 'MMMM DD YYYY';
  @Input() public milliseconds = 0;
  @Input() public format ? = MillisecondToDateComponent.MMMM_DD_YYYY;
  public formattedDate = '';

  constructor() {}

  ngOnInit() {
    this.formattedDate = moment(this.milliseconds).format(this.format);
  }

}
