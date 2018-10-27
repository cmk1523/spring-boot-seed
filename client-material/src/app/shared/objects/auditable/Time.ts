import {Auditable} from './Auditable';
import {ValidationResponse} from '../ValidationResponse';
import {User} from './User';
import {Rate} from './Rate';
import {Company} from './Company';
import {Location} from './Location';

export class Time extends Auditable {
  public static TIMES_TYPES: string[] = ['Type 1', 'Type 2'];

  rate: Rate = null;
  company: Company = null;
  user: User = null;
  date = 0;
  reportId: number = null;
  type: '';
  jobId = '';
  location: Location = null;

  // Other
  insidePerimeter: boolean = null;
  icDailyRate = 0;
  supplementalRate = 0;
  fieldServicesPremium = 0;
  specialProjectPremium = 0;
  miscTravelCosts: number = null;
  mie_breakfast = 0;
  mie_lunch = 0;
  mie_dinner = 0;
  mie_incidentals = 0;
  mie: number = null;
  lodging = 0;
  localTransportation: number = null;
  povMileage: number = null;
  calcMileageReimbursement = 0;
  total = 0;

  constructor(i: any = {}) {
    super(i);
    Object.assign(this, i);
    this.user = (i.user) ? new User(i.user) : this.user;
    this.company = (i.company) ? new Company(i.company) : this.company;
    this.rate = (i.rate) ? new Rate(i.rate) : this.rate;
  }

  public static Validate(i: Time, isNew = false): ValidationResponse {
    let vr = Auditable.Validate(i, isNew);
    if (!vr.isValid) {
      console.error('Time - Validate - invalid Auditable: ' + vr.message);
      return vr;
    }

    vr = Rate.Validate(i.rate, isNew);
    if (!vr.isValid) {
      console.error('Time - Validate - invalid Rate: ' + vr.message);
      return vr;
    }

    vr = Company.Validate(i.company, isNew);
    if (!vr.isValid) {
      console.error('Time - Validate - invalid Company: ' + vr.message);
      return vr;
    }

    vr = User.Validate(i.user, isNew);
    if (!vr.isValid) {
      console.error('Time - Validate - invalid User: ' + vr.message);
      return vr;
    }

    vr = Location.Validate(i.location, isNew);
    if (!vr.isValid) {
      console.error('Time - Validate - invalid Location: ' + vr.message);
      return vr;
    }

    if (i.date == null || i.date === 0) {
      console.error('Time - Validate - invalid date');
      return new ValidationResponse(false, 'date', 'Time date is null or empty');
    }

    if (i.reportId == null || i.reportId === 0) {
      console.error('Time - Validate - invalid reportId');
      return new ValidationResponse(false, 'reportId', 'Time reportId is null or empty');
    }

    if (i.type == null || i.type === '') {
      console.error('Time - Validate - invalid type');
      return new ValidationResponse(false, 'type', 'Time type is null or empty');
    }

    if (i.jobId == null || i.jobId === '') {
      console.error('Time - Validate - invalid jobId');
      return new ValidationResponse(false, 'jobId', 'Time jobId is null or empty');
    }

    return new ValidationResponse(true, '', '');
  }

  recalculateTime(time: Time, appInfo: any) {
    const location = time.location.name;
    const jobId = time.jobId;
    time.insidePerimeter = (location === 'INTL') ? time.insidePerimeter : false;
    time.supplementalRate = this.calculateSupplementalRate(location, jobId);
    time.fieldServicesPremium = this.calculateFieldServicesPremium(location, jobId);
    time.specialProjectPremium = this.calculateSpecialProjectsPremium(location, jobId);
    time.mie = parseFloat(time.mie_breakfast.toString()) +
      parseFloat(time.mie_lunch.toString()) +
      parseFloat(time.mie_dinner.toString()) +
      parseFloat(time.mie_incidentals.toString());
    time.calcMileageReimbursement = appInfo.settings.povRate * parseFloat(time.povMileage.toString());
    time.total = parseFloat(time.icDailyRate.toString()) +
      time.supplementalRate +
      time.fieldServicesPremium +
      time.specialProjectPremium +
      parseFloat(time.miscTravelCosts.toString()) +
      time.mie +
      parseFloat(time.lodging.toString()) +
      parseFloat(time.localTransportation.toString()) +
      time.calcMileageReimbursement
    ;
    return time;
  }

  public calculateFieldServicesPremium(location: string, jobId: string) {
    // TODO: calculate field services premium
    return 1.00;
  }

  public calculateSupplementalRate(location: string, jobId: string) {
    // TODO: calculate Supplemental Rate
    return 142.00;
  }

  public calculateSpecialProjectsPremium(location: string, jobId: string) {
    // TODO: calculate Special Projects Premium
    return 219.00;
  }
}
