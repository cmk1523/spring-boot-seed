import {Component, Input, OnInit} from '@angular/core';

import * as moment from 'moment';
import {MillisecondToDate} from '../../pipes/MillisecondToDate.pipe';

@Component({
  selector: 'app-millisecond-to-date',
  templateUrl: './millisecond-to-date.component.html',
  styleUrls: ['./millisecond-to-date.component.css']
})
export class MillisecondToDateComponent implements OnInit {
  public static DDMMMYYYY = 'DDMMMYYYY';
  public static MMDDYYYY_HH_MM_SS = 'MM-DD-YYYY HH:mm:ss';
  public static DDMMMYYYY_HH_MM_SS = 'DDMMMYYYY HH:mm:ss';
  public static DDMMMYYYY_HH_MM_SS_Z = 'DDMMMYYYY HH:mm:ss Z';
  public static MMMM_DD_YYYY = 'MMMM DD, YYYY';
  public static MM_DD_YYYY = 'MM/DD/YYYY';

  @Input() public milliseconds = 0;
  @Input() public format ? = MillisecondToDateComponent.MM_DD_YYYY;
  public formattedDate = '';

  constructor() {}

  ngOnInit() {
    this.formattedDate = moment(this.milliseconds).format(this.format);
  }

}
