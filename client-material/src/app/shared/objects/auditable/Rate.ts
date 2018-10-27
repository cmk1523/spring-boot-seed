import {Auditable} from './Auditable';
import {ValidationResponse} from '../ValidationResponse';
import {Company} from './Company';

export class Rate extends Auditable {
  company: Company = null;
  amount = 0.00;

  constructor(i: any = {}) {
    super(i);
    Object.assign(this, i);
    this.company = (i.company) ? new Company(i.company) : this.company;
  }

  public static Validate(i: Rate, isNew = false): ValidationResponse {
    let vr = Auditable.Validate(i, isNew);
    if (!vr.isValid) {
      console.error('Rate - Validate - invalid Auditable: ' + vr.message);
      return vr;
    }

    vr = Company.Validate(i.company, isNew);
    if (!vr.isValid) {
      console.error('Rate - Validate - invalid Company: ' + vr.message);
      return vr;
    }

    if (i.amount == null || i.amount === 0) {
      console.error('Rate - Validate - invalid amount');
      return new ValidationResponse(false, 'amount', 'Rate amount is null or 0');
    }

    return new ValidationResponse(true, '', '');
  }
}
