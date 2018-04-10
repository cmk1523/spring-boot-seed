import {ValidationResponse} from '../ValidationResponse';

export class Auditable {
  private _id = 0;
  private _name = '';
  private _createdDate = 0;
  private _createdBy = '';
  private _updatedDate = 0;
  private _updatedBy = '';
  private _removed = false;

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

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get createdDate(): number {
    return this._createdDate;
  }

  set createdDate(value: number) {
    this._createdDate = value;
  }

  get createdBy(): string {
    return this._createdBy;
  }

  set createdBy(value: string) {
    this._createdBy = value;
  }

  get updatedDate(): number {
    return this._updatedDate;
  }

  set updatedDate(value: number) {
    this._updatedDate = value;
  }

  get updatedBy(): string {
    return this._updatedBy;
  }

  set updatedBy(value: string) {
    this._updatedBy = value;
  }

  get removed(): boolean {
    return this._removed;
  }

  set removed(value: boolean) {
    this._removed = value;
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
