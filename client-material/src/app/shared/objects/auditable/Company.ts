import {Auditable} from './Auditable';
import {ValidationResponse} from '../ValidationResponse';

export class Company extends Auditable {
  baseColor = '#3f51b5';
  logos: {
    '32': '',
    '64': '',
    '128': ''
  };

  constructor(i: any = {}) {
    super(i);
    Object.assign(this, i);
  }

  public static Validate(i: Company, isNew = false): ValidationResponse {
    const vr = Auditable.Validate(i, isNew);
    if (!vr.isValid) {
      console.error('Company - Validate - invalid Auditable: ' + vr.message);
      return vr;
    }

    return new ValidationResponse(true, '', '');
  }
}
