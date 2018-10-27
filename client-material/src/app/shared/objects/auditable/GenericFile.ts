import {Auditable} from './Auditable';
import {ValidationResponse} from '../ValidationResponse';

export class GenericFile extends Auditable {
  base64 = '';
  path = '';
  type = '';
  md5 = '';
  lastModified = null;
  size = null;

  constructor(i: any = {}) {
    super(i);
    Object.assign(this, i);
  }

  public static Validate(i: GenericFile, isNew = false): ValidationResponse {
    const vr = Auditable.Validate(i, isNew);
    if (!vr.isValid) {
      console.error('GenericFile - Validate - invalid Auditable: ' + vr.message);
      return vr;
    }

    return new ValidationResponse(true, '', '');
  }
}
