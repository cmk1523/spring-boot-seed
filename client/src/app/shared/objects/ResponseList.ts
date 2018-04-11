import {Response} from './Response';

export class ResponseList extends Response {
  private _size = 0;

  constructor(i: any = {}) {
    super();
    Object.assign(this, i);
  }


  get size(): number {
    return this._size;
  }

  set size(value: number) {
    this._size = value;
  }
}
