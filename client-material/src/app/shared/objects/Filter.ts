import {HttpParams} from '@angular/common/http';

export class Filter {
  public static SORT_ASC = 'asc';
  public static SORT_DESC = 'desc';
  public static FILTER_LOGIC_AND = 'and';
  public static FILTER_LOGIC_OR = 'or';
  public static FILTER_LOGIC_NOT = 'not';

  public static DEFAULT_SIZE = 10;
  public static DEFAULT_OFFSET = 0;
  public static DEFAULT_ORDER = Filter.SORT_DESC;
  public static DEFAULT_SORT = 'updatedDate';
  public static DEFAULT_FILTER_LOGIC = Filter.FILTER_LOGIC_AND;

  size: number = Filter.DEFAULT_SIZE;
  offset: number = Filter.DEFAULT_OFFSET;
  sort: string = Filter.DEFAULT_SORT;
  order: string = Filter.DEFAULT_ORDER;
  filters = '';
  filterLogic: string = Filter.FILTER_LOGIC_AND;

  public static ToUrlParams(filter: Filter) {
    return new HttpParams()
      .set('size', filter.size.toString())
      .set('offset', filter.offset.toString())
      .set('sort', filter.sort)
      .set('order', filter.order)
      .set('filters', filter.filters)
      .set('filterLogic', filter.filterLogic);
  }

  public static ToOptionalUrlParams(filter: Filter) {
    return '?size=' + filter.size +
      '&offset=' + filter.offset +
      '&sort=' + filter.sort +
      '&order=' + filter.order +
      '&filters=' + filter.filters +
      '&filterLogic=' + filter.filterLogic;
  }

  constructor(i: any = {}) {
    Object.assign(this, i);
  }
}
