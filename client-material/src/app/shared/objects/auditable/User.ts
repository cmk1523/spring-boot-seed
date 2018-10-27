import {Auditable} from './Auditable';
import {ValidationResponse} from '../ValidationResponse';
import {Company} from './Company';
import {Rate} from './Rate';
import {Role} from './Role';

export class User extends Auditable {
  public static TYPE_USER = 'User';
  public static TYPE_PROGRAM_MANAGER = 'Program Manager';
  public static TYPE_ACCOUNTANT = 'Accountant';
  public static TYPE_MANAGEMENT = 'Management';

  username = '';
  type = '';
  company: Company = null;
  rate: Rate = null;
  role: Role = null;

  constructor(i: any = {}) {
    super(i);
    Object.assign(this, i);
    this.company = (i.company) ? new Company(i.company) : this.company;
    this.rate = (i.rate) ? new Rate(i.rate) : this.rate;
  }

  public static Validate(i: User, isNew = false): ValidationResponse {
    let vr = Auditable.Validate(i, isNew);
    if (!vr.isValid) {
      console.error('User - Validate - invalid Auditable: ' + vr.message);
      return vr;
    }

    vr = Company.Validate(i.company, isNew);
    if (!vr.isValid) {
      console.error('User - Validate - invalid Company: ' + vr.message);
      return vr;
    }

    // vr = Rate.Validate(i.rate, isNew);
    // if (!vr.isValid) {
    //   console.error('User - Validate - invalid Rate: ' + vr.message);
    //   return vr;
    // }
    //
    // vr = Role.Validate(i.role, isNew);
    // if (!vr.isValid) {
    //   console.error('User - Validate - invalid Role: ' + vr.message);
    //   return vr;
    // }

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
    return this.rate != null && this.role != null;
  }
}
