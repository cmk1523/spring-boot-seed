import {Filter} from './Filter';

export class Search extends Filter {
  public term = '';

  constructor(i: any = {}) {
    super();
    Object.assign(this, i);
  }
}
