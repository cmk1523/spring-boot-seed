export class Response {
  private _data: any = null;
  private _took = 0;
  private _error = '';

  constructor(i: any = {}) {
    Object.assign(this, i);
  }

  get took(): number {
    return this._took;
  }

  set took(value: number) {
    this._took = value;
  }

  get error(): string {
    return this._error;
  }

  set error(value: string) {
    this._error = value;
  }

  get data(): any {
    return this._data;
  }

  set data(value: any) {
    this._data = value;
  }
}
