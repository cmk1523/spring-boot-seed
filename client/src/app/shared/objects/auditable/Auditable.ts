import {ValidationResponse} from '../ValidationResponse';

export class Auditable {
  id = 0;
  name = '';
  createdDate = 0;
  createdBy = '';
  updatedDate = 0;
  updatedBy = '';
  removed = false;

  public static Validate(i: Auditable, isNew = false): ValidationResponse {
    if (i == null) {
      return new ValidationResponse(false, '', 'Auditable is null');
    }

    if (!isNew && (i.id == null || i.id === 0)) {
      return new ValidationResponse(false, 'id', 'Auditable id is null or empty');
    }

    if (i.name == null || i.name === '') {
      return new ValidationResponse(false, 'name', 'Auditable name is null or empty');
    }

    if (i.createdDate == null) {
      return new ValidationResponse(false, 'createdDate', 'Auditable createdDate is null');
    }

    if (i.createdBy == null || i.createdBy === '') {
      return new ValidationResponse(false, 'createdBy', 'Auditable createdBy is null or empty');
    }

    if (i.updatedDate == null) {
      return new ValidationResponse(false, 'updatedDate', 'Auditable updatedDate is null');
    }

    if (i.updatedBy == null || i.updatedBy === '') {
      return new ValidationResponse(false, 'updatedBy', 'Auditable updatedBy is null or empty');
    }

    if (i.removed == null) {
      return new ValidationResponse(false, 'removed', 'Auditable removed is null');
    }

    return new ValidationResponse(true, '', '');
  }

  constructor(i: any = {}) {
    Object.assign(this, i);
  }

  toJsonString(): string {
    let json = JSON.stringify(this);
    Object.keys(this).filter(key => key[0] === '_').forEach(key => {
      json = json.replace(key, key.substring(1));
    });
    return json;
  }
}
