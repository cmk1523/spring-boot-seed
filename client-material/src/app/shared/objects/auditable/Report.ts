import {Auditable} from './Auditable';
import {ValidationResponse} from '../ValidationResponse';
import {Expense} from './Expense';
import {Time} from './Time';
import {User} from './User';
import * as moment from 'moment';
import {MillisecondToDateComponent} from '../../components/millisecond-to-date/millisecond-to-date.component';

export class Report extends Auditable {
  public static STATUS_CODE_1_MESSAGE = 'Not Submitted';
  public static STATUS_CODE_2_MESSAGE = 'Awaiting PM Approval';
  public static STATUS_CODE_3_MESSAGE = 'Awaiting Accountant Approval';
  public static STATUS_CODE_4_MESSAGE = 'Awaiting 2nd PM Approval';
  public static STATUS_CODE_5_MESSAGE = 'Awaiting Management Approval';
  public static STATUS_CODE_6_MESSAGE = 'Complete';

  public static STATUS_CODE_1 = 1;
  public static STATUS_CODE_2 = 2;
  public static STATUS_CODE_3 = 3;
  public static STATUS_CODE_4 = 4;
  public static STATUS_CODE_5 = 5;
  public static STATUS_CODE_6 = 6;
  public static STATUS_CODE_MAX = 6;

  expenses: Expense[] = [];
  times: Time[] = [];
  user: User = null;
  firstDayOfMonth: number = null;
  // status = Report.STATUS_NOT_SUBMITTED;
  statusCode = 0;
  comment = '';
  history: Report[] = [];

  constructor(i: any = {}) {
    super(i);
    Object.assign(this, i);
  }

  public static Validate(i: Report, isNew = false): ValidationResponse {
    let vr = Auditable.Validate(i, isNew);
    if (!vr.isValid) {
      console.error('Report - Validate - invalid Auditable: ' + vr.message);
      return vr;
    }

    vr = User.Validate(i.user, isNew);
    if (!vr.isValid) {
      console.error('Report - Validate - invalid User: ' + vr.message);
      return vr;
    }

    i.expenses.forEach((expense: Expense) => {
      vr = Expense.Validate(expense, isNew);
      if (!vr.isValid) {
        console.error('Report - Validate - invalid Expense: ' + vr.message);
        return vr;
      }
    });

    i.times.forEach((time: Time) => {
      vr = Time.Validate(time, isNew);
      if (!vr.isValid) {
        console.error('Report - Validate - invalid Time: ' + vr.message);
        return vr;
      }
    });

    if (i.firstDayOfMonth == null || i.firstDayOfMonth === 0) {
      console.error('Report - Validate - invalid firstDayOfMonth');
      return new ValidationResponse(false, 'firstDayOfMonth', 'Report firstDayOfMonth is null or empty');
    }

    if (i.statusCode == null || i.statusCode === 0) {
      console.error('Report - Validate - invalid statusCode');
      return new ValidationResponse(false, 'statusCode', 'Report statusCode is null or empty');
    }

    if (i.comment == null || i.comment === '') {
      console.error('Report - Validate - invalid comment');
      return new ValidationResponse(false, 'comment', 'Report comment is null or empty');
    }

    return new ValidationResponse(true, '', '');
  }

  public static StatusCodeToStatus(i: number) {
    if (i === 1) {
      return Report.STATUS_CODE_1_MESSAGE;
    } else if (i === 2) {
      return Report.STATUS_CODE_2_MESSAGE;
    } else if (i === 3) {
      return Report.STATUS_CODE_3_MESSAGE;
    } else if (i === 4) {
      return Report.STATUS_CODE_4_MESSAGE;
    } else if (i === 5) {
      return Report.STATUS_CODE_5_MESSAGE;
    } else if (i === 6) {
      return Report.STATUS_CODE_6_MESSAGE;
    } else {
      console.error('Report - unknown status code: ' + i);
      return '';
    }
  }

  public static CanEdit(report: Report, user: User) {
    if (report.statusCode === 1 && user.type === User.TYPE_USER) {
      return true;
    } else if (report.statusCode === 2 && user.type === User.TYPE_PROGRAM_MANAGER) {
      return true;
    } else if (report.statusCode === 3 && user.type === User.TYPE_ACCOUNTANT) {
      return true;
    } else if (report.statusCode === 4 && user.type === User.TYPE_PROGRAM_MANAGER) {
      return true;
    } else if (report.statusCode === 5 && user.type === User.TYPE_PROGRAM_MANAGER) {
      return true;
    } else {
      return false;
    }
  }

  public getTotalExpenses() {
    let total = 0.00;

    this.expenses.forEach((i: Expense) => {
      total += parseFloat(i.amount.toString());
    });

    return total;
  }

  public getTotalTimes() {
    let total = 0.00;

    this.times.forEach((i: Time) => {
      // i = this.recalculateTime(i);
      total += parseFloat(i.total.toString());
    });

    return total;
  }

  generateReport(appInfo: any): string {
    const rows: any[] = [
      ['ID', this.id],
      ['User', this.user.name],
      ['Month', moment(this.firstDayOfMonth).format('MMMM')],
      ['Name', this.name],
      ['Current Status', Report.StatusCodeToStatus(this.statusCode)],
      ['Created', moment(this.createdDate).format(MillisecondToDateComponent.MM_DD_YYYY)],
      ['Last Updated', moment(this.updatedDate).format(MillisecondToDateComponent.MM_DD_YYYY)],
      ['Comment', this.comment],
      [],
    ];

    rows.push(['Expenses']);
    rows.push(['Date', 'Location', 'Job Identifier', 'Expense Type', 'Comments', 'Amount']);

    this.expenses.forEach((i: Expense) => {
      rows.push([
        moment(i.date).format(MillisecondToDateComponent.MM_DD_YYYY),
        i.location.name,
        i.jobId,
        i.type,
        i.comment,
        i.amount
      ]);
    });

    rows.push([]);
    rows.push(['Times']);
    rows.push(['Day', 'Location', 'Job Identifier', 'Inside Perimeter', 'IC Daily Rate', 'Supplemental Rate',
      'Special Project Premium', 'Special Project Skills or Risk Premium', 'Misc Travel Costs', 'M&IE Breakfast',
      'M&IE Lunch', 'M&IE Dinner', 'M&IE Incidentals', 'M&IE Total', 'Lodging', 'Local Transportation', 'POV Mileage',
      'Calculated Mileage Reimbursement', 'Total']);

    // let currentDay = moment(this.firstDayOfMonth).subtract(1, 'months').endOf('month');

    for (let d = 1; d <= 31; d++) {
      const i = d;
      // currentDay.add(1, 'day');

      const dayToFind = this.times.filter((time: Time) => {
        const dayOfMonth = parseInt(moment(time.date).format('D'), 10) - 1;
        return dayOfMonth === d;
      });

      if (dayToFind.length === 0) {
        // currentDay.format(MillisecondToDateComponent.MM_DD_YYYY)
        rows.push([d,
          '',
          '',
          '',
          '',
          '',
          '',
          '',
          '',
          '',
          '',
          '',
          '',
          '',
          '',
          '',
          '',
          '',
          ''
        ]);
      } else {
        const time = dayToFind[0];
        // moment(i.date).format(MillisecondToDateComponent.MM_DD_YYYY)
        rows.push([d,
          time.location.name,
          time.jobId,
          time.insidePerimeter,
          time.icDailyRate,
          time.supplementalRate,
          time.fieldServicesPremium,
          time.specialProjectPremium,
          time.miscTravelCosts,
          time.mie_breakfast,
          time.mie_lunch,
          time.mie_dinner,
          time.mie_incidentals,
          time.mie,
          time.lodging,
          time.localTransportation,
          time.povMileage,
          time.calcMileageReimbursement,
          time.total
        ]);
      }
    }

    rows.push([]);
    rows.push(['Totals']);
    rows.push(['Expense Total', this.getTotalExpenses()]);
    rows.push(['Time Total', this.getTotalTimes()]);
    rows.push(['Total Amount Due', this.getTotalExpenses() + this.getTotalTimes()]);


    let csvContent = 'data:text/csv;charset=utf-8,\r\n';
    rows.forEach((rowArray) => {
      const row = rowArray.join(',');
      csvContent += row + '\r\n';
    });

    return csvContent;
  }

  generateReportExportName() {
    return 'Report_Export_' + this.id + '.csv';
  }

  downloadReport(appInfo: any, csvContent: any = null) {
    if (csvContent == null) {
      csvContent = this.generateReport(appInfo);
    }

    const csvData = new Blob([csvContent], {type: 'text/csv;charset=utf-8;'});
    // IE11 & Edge
    if (navigator.msSaveBlob) {
      navigator.msSaveBlob(csvData, this.generateReportExportName());
    } else {
      // In FF link must be added to DOM to be clicked
      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(csvData);
      link.setAttribute('download', this.generateReportExportName());
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    }
  }
}
