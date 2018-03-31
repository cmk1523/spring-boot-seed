export class Response {
  private _data: any = null;
  private _tool = 0;
  private _error = '';

  constructor() {
  }

  get tool(): number {
    return this._tool;
  }

  set tool(value: number) {
    this._tool = value;
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
