export class ValidationResponse {
  private _field = '';
  private _message = '';
  private _isValid = false;

  constructor(isValid: boolean, field: string, message: string) {
    this._isValid = isValid;
    this._field = field;
    this._message = message;
  }

  get field() {
    return this._field;
  }

  set field(value) {
    this._field = value;
  }

  get message() {
    return this._message;
  }

  set message(value) {
    this._message = value;
  }

  get isValid(): boolean {
    return this._isValid;
  }

  set isValid(value: boolean) {
    this._isValid = value;
  }
}
