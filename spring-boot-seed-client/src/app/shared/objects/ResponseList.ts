import {Response} from './Response';

export class ResponseList extends Response {
  private _size = 0;

  constructor() {
    super();
  }


  get size(): number {
    return this._size;
  }

  set size(value: number) {
    this._size = value;
  }
}
