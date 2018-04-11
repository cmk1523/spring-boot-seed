import {Auditable} from './Auditable';
import {ValidationResponse} from '../ValidationResponse';

export class User extends Auditable {
  constructor(i: any = {}) {
    super(i);
    Object.assign(this, i);
  }

  public static Validate(i: Auditable, isNew = false): ValidationResponse {
    if (!Auditable.Validate(i, isNew).isValid) {
      return Auditable.Validate(i, isNew);
    }

    return new ValidationResponse(true, '', '');
  }
}
