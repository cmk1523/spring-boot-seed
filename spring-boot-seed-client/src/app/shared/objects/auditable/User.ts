import {Auditable} from './Auditable';
import {ValidationResponse} from '../ValidationResponse';

export class User extends Auditable {
  constructor(i: any = {}) {
    super(i);
    Object.assign(this, i);
  }

  public static Validate(i: Auditable): ValidationResponse {
    if (!Auditable.Validate(i).isValid) {
      return Auditable.Validate(i);
    }

    return new ValidationResponse(true, '', '');
  }
}
