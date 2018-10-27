import {Auditable} from './Auditable';
import {ValidationResponse} from '../ValidationResponse';
import {User} from './User';
import {Company} from './Company';
import {Rate} from './Rate';
import {GenericFile} from './GenericFile';
import {Location} from './Location';

export class Expense extends Auditable {
  user: User = null;
  company: Company = null;
  rate: Rate = null;
  reportId: number = null;
  chargeCode = '';
  files: GenericFile[] = [];
  amount: number = null;
  comment = '';
  type = '';
  jobId = '';
  date = new Date().getTime();
  paymentType = '';
  location: Location = null;

  constructor(i: any = {}) {
    super(i);
    Object.assign(this, i);
    this.user = (i.user) ? new User(i.user) : this.user;
    this.company = (i.company) ? new Company(i.company) : this.company;
    this.rate = (i.rate) ? new Rate(i.rate) : this.rate;
  }

  public static Validate(i: Expense, isNew = false): ValidationResponse {
    let vr = Auditable.Validate(i, isNew);
    if (!vr.isValid) {
      console.error('Expense - Validate - invalid Auditable: ' + vr.message);
      return vr;
    }

    vr = User.Validate(i.user, isNew);
    if (!vr.isValid) {
      console.error('Expense - Validate - invalid User: ' + vr.message);
      return vr;
    }

    vr = Company.Validate(i.company, isNew);
    if (!vr.isValid) {
      console.error('Expense - Validate - invalid Company: ' + vr.message);
      return vr;
    }

    vr = Rate.Validate(i.rate, isNew);
    if (!vr.isValid) {
      console.error('Expense - Validate - invalid Rate: ' + vr.message);
      return vr;
    }

    vr = Location.Validate(i.location, isNew);
    if (!vr.isValid) {
      console.error('Expense - Validate - invalid Location: ' + vr.message);
      return vr;
    }

    if (i.reportId == null || i.reportId === 0) {
      console.error('Expense - Validate - invalid reportId');
      return new ValidationResponse(false, 'reportId', 'Expense reportId is null or empty');
    }

    if (i.chargeCode == null || i.chargeCode === '') {
      console.error('Expense - Validate - invalid chargeCode');
      return new ValidationResponse(false, 'chargeCode', 'Expense chargeCode is null or empty');
    }

    i.files.forEach((image: GenericFile) => {
      vr = GenericFile.Validate(image, isNew);
      if (!vr.isValid) {
        console.error('Expense - Validate - invalid GenericFile: ' + vr.message);
        return vr;
      }
    });

    if (i.files == null || i.files.length === 0) {
      console.error('Expense - Validate - invalid files');
      return new ValidationResponse(false, 'amount', 'Expense must have at least one file');
    }

    if (i.amount == null) {
      console.error('Expense - Validate - invalid amount');
      return new ValidationResponse(false, 'amount', 'Expense amount is null or empty');
    }

    if (i.type == null || i.type === '') {
      console.error('Expense - Validate - invalid type');
      return new ValidationResponse(false, 'type', 'Expense type is null or empty');
    }

    if (i.jobId == null || i.jobId === '') {
      console.error('Expense - Validate - invalid jobId');
      return new ValidationResponse(false, 'jobId', 'Expense jobId is null or empty');
    }

    if (i.date == null) {
      console.error('Auditable - Validate - invalid date');
      return new ValidationResponse(false, 'date', 'Auditable date is null');
    }

    if (i.paymentType == null || i.paymentType === '') {
      console.error('Expense - Validate - invalid paymentType');
      return new ValidationResponse(false, 'paymentType', 'Expense paymentType is null or empty');
    }

    return new ValidationResponse(true, '', '');
  }
}
