import {ValidationResponse} from '../ValidationResponse';

export class Auditable {
  id = 0;
  name = '';
  createdDate = new Date().getTime();
  createdBy = '';
  updatedDate = new Date().getTime();
  updatedBy = '';
  removed = false;
  version = 1;

  public static Validate(i: Auditable, isNew = false): ValidationResponse {
    if (i == null) {
      console.error('Auditable - Validate - invalid');
      return new ValidationResponse(false, '', 'Auditable is null');
    }

    if (!isNew && (i.id == null || i.id === 0)) {
      console.error('Auditable - Validate - invalid id');
      return new ValidationResponse(false, 'id', 'Auditable id is null or empty');
    }

    if (i.name == null || i.name === '') {
      console.error('Auditable - Validate - invalid name');
      return new ValidationResponse(false, 'name', 'Auditable name is null or empty');
    }

    if (i.createdDate == null) {
      console.error('Auditable - Validate - invalid createdDate');
      return new ValidationResponse(false, 'createdDate', 'Auditable createdDate is null');
    }

    if (i.createdBy == null || i.createdBy === '') {
      console.error('Auditable - Validate - invalid createdBy');
      return new ValidationResponse(false, 'createdBy', 'Auditable createdBy is null or empty');
    }

    if (i.updatedDate == null) {
      console.error('Auditable - Validate - invalid updatedDate');
      return new ValidationResponse(false, 'updatedDate', 'Auditable updatedDate is null');
    }

    if (i.updatedBy == null || i.updatedBy === '') {
      console.error('Auditable - Validate - invalid updatedBy');
      return new ValidationResponse(false, 'updatedBy', 'Auditable updatedBy is null or empty');
    }

    if (i.removed == null) {
      console.error('Auditable - Validate - invalid removed');
      return new ValidationResponse(false, 'removed', 'Auditable removed is null');
    }

    if (i.version == null || i.version === 0) {
      console.error('Auditable - Validate - invalid version');
      return new ValidationResponse(false, 'version', 'Auditable version is null or empty');
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

  removeGettersSetters(): any {
    return JSON.parse(this.toJsonString());
  }
}
