import {Auditable} from './Auditable';
import {ValidationResponse} from '../ValidationResponse';

export class User extends Auditable {
  username = '';
  type = '';

  constructor(i: any = {}) {
    super(i);
    Object.assign(this, i);
  }

  public static Validate(i: User, isNew = false): ValidationResponse {
    let vr = Auditable.Validate(i, isNew);
    if (!vr.isValid) {
      console.error('User - Validate - invalid Auditable: ' + vr.message);
      return vr;
    }

    if (i.username == null || i.username === '') {
      console.error('Auditable - Validate - invalid username');
      return new ValidationResponse(false, 'username', 'Auditable username is null or empty');
    }

    if (i.type == null || i.type === '') {
      console.error('Auditable - Validate - invalid type');
      return new ValidationResponse(false, 'type', 'Auditable type is null or empty');
    }

    return new ValidationResponse(true, '', '');
  }

  isAdmin() {
    return true; // this.role && this.role.name === 'admins';
  }

  isEnabled() {
    return true;
  }
}
