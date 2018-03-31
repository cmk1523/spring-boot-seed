import {Pipe, PipeTransform} from '@angular/core';
import * as moment from 'moment';

@Pipe({
  name: 'millisecondToDate'
})
export class MillisecondToDate implements PipeTransform {
  public static DDMMMYYYY = 'DDMMMYYYY';
  public static DDMMMYYYY_HH_MM_SS = 'DDMMMYYYY HH:mm:ss';
  public static DDMMMYYYY_HH_MM_SS_Z = 'DDMMMYYYY HH:mm:ss Z';
  public static MMMM_DD_YYYY = 'MMMM DD, YYYY';
  public static FROM_NOW = 'FROM_NOW';

  transform(value: number, format: string = MillisecondToDate.DDMMMYYYY): string {
    if (format === MillisecondToDate.DDMMMYYYY_HH_MM_SS_Z) {
      return moment(value).utc().format(format).toUpperCase().replace(' +00:00', ' Z');
    } else if (format === MillisecondToDate.FROM_NOW) {
      return moment(value).fromNow();
    } else {
      return moment(value).format(format);
    }
  }
}
