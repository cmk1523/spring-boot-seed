import {Auditable} from './Auditable';
import {ValidationResponse} from '../ValidationResponse';

export class Location extends Auditable {
  jobIds: string[] = [];

  constructor(i: any = {}) {
    super(i);
    Object.assign(this, i);
  }

  public static Validate(i: Location, isNew = false): ValidationResponse {
    const vr = Auditable.Validate(i, isNew);
    if (!vr.isValid) {
      console.error('Location - Validate - invalid Auditable: ' + vr.message);
      return vr;
    }

    return new ValidationResponse(true, '', '');
  }
}
