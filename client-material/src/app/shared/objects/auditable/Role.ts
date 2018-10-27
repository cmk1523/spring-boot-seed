import {Auditable} from './Auditable';
import {ValidationResponse} from '../ValidationResponse';

export class Role extends Auditable {
  constructor(i: any = {}) {
    super(i);
    Object.assign(this, i);
  }

  public static Validate(i: Role, isNew = false): ValidationResponse {
    // const vr = Auditable.Validate(i, isNew);
    // if (!vr.isValid) {
    //   console.error('Role - Validate - invalid Auditable: ' + vr.message);
    //   return vr;
    // }

    return new ValidationResponse(true, '', '');
  }
}
