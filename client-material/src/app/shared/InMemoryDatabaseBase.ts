import {InMemoryDbService, ResponseOptions} from 'angular-in-memory-web-api';
import {User} from './objects/auditable/User';
import {ResponseList} from './objects/ResponseList';
import {Response} from './objects/Response';
import {Filter} from './objects/Filter';

export class InMemoryDatabaseBase {
  public static TestUser = 'Administrator';
  public static HTTP_GET = 'get';
  public static HTTP_PUT = 'put';
  public static HTTP_POST = 'post';
  public static HTTP_DELETE = 'delete';

  adminRole = null;

  constructor() {
  }

  urlHasParam(url: string, endpoint: string): boolean {
    return url.indexOf(endpoint) > -1;
  }

  getOptionalUrlParams(url: string): any {
    const response: any = {};
    const pos = url.indexOf('?');
    if (pos === -1) { return response; }
    const params = url.substr(pos + 1);
    const keyValues: string[] = params.split('&');

    keyValues.forEach((i: string) => {
      const split: string[] = i.split('=');

      if (split.length === 2) {
        const key: string = split[0];
        const value: string = split[1];
        response[key] = value;

        if (!isNaN(parseInt(value, 10))) {
          response[key] = parseInt(value, 10);
        }
      }
    });

    return response;
  }

  hasAdminAccess(user: User) {
    return user.role.name === this.adminRole.name;
  }

  getKeyValue(filterStr: string): any {
    const split: string[] = filterStr.split('::');

    if (split.length === 2) {
      return {
        key: split[0],
        value: (!isNaN(parseInt(split[1], 10))) ? parseInt(split[1], 10) : split[1]
      };
    }

    return {};
  }

  getGreaterThanValue(filterStr: string): any {
    let split: string[] = filterStr.split('>');

    if (filterStr.indexOf('>') === -1) {
      split = filterStr.split('%3E');
    }

    if (split.length === 2) {
      return {
        key: split[0],
        value: (!isNaN(parseInt(split[1], 10))) ? parseInt(split[1], 10) : split[1]
      };
    }

    return {};
  }

  getLessThanValue(filterStr: string): any {
    let split: string[] = filterStr.split('<');

    if (filterStr.indexOf('<') === -1) {
      split = filterStr.split('%3C');
    }

    if (split.length === 2) {
      return {
        key: split[0],
        value: (!isNaN(parseInt(split[1], 10))) ? parseInt(split[1], 10) : split[1]
      };
    }

    return {};
  }

  generateInvalidInputResponse(data: any, resOptions: ResponseOptions) {
    resOptions.body = null;
    resOptions['error'] = {
      status: 500,
      error: 'InMemoryDatabase Error',
      message: 'Invalid input: ' + data
    };
    resOptions.status = 500;
  }

  generateUnableToFindItemResponse(id: any, resOptions: ResponseOptions) {
    resOptions.body = null;
    resOptions['error'] = {
      status: 500,
      error: 'InMemoryDatabase Error',
      message: 'Unable to find item: ' + id
    };
    resOptions.status = 500;
  }

  generateItemAlreadyExistsResponse(resOptions: ResponseOptions) {
    resOptions.body = null;
    resOptions['error'] = {
      status: 500,
      error: 'InMemoryDatabase Error',
      message: 'Item already exists'
    };
    resOptions.status = 500;
  }

  generateItemForbiddenResponse(resOptions: ResponseOptions) {
    resOptions.body = null;
    resOptions['error'] = {
      status: 401,
      error: 'InMemoryDatabase Error',
      message: 'User is authorized to access this item'
    };
    resOptions.status = 401;
  }

  generateGetResponseList(data: any, resOptions: ResponseOptions) {
    resOptions.body = new ResponseList( { took: 1, data: data, size: data.size } );
    resOptions.status = 200;
    resOptions.statusText = '';
    return resOptions;
  }

  generateGetResponse(data: any, resOptions: ResponseOptions) {
    resOptions.body = new Response({ took: 1, data: data } );
    resOptions.status = 200;
    resOptions.statusText = '';
    return resOptions;
  }

  generateCreateResponse(data: any, resOptions: ResponseOptions) {
    resOptions.body = new Response({ took: 1, data: data } );
    resOptions.status = 201;
    resOptions.statusText = '';
    return resOptions;
  }

  generateDeleteResponse(resOptions: ResponseOptions) {
    resOptions.body = new Response({ took: 1, data: null } );
    resOptions.status = 202;
    resOptions.statusText = '';
    return resOptions;
  }

  getLastRestVariable(s: string) {
    const split = s.split('/');
    return split[split.length - 1];
  }

  determineFilter(filters: any) {
    let keyValue = this.getKeyValue(filters);
    if (keyValue.key) { return 'equals'; }
    keyValue = this.getGreaterThanValue(filters);
    if (keyValue.key) { return 'greaterThan'; }
    keyValue = this.getLessThanValue(filters);
    if (keyValue.key) { return 'lessThan'; }
    return '';
  }

  applyEqualsFilter(filtersStr, list) {
    const keyValue = this.getKeyValue(filtersStr);

    if (keyValue.key) {
      const keys = keyValue.key.split('.');

      list = list.filter((i: any) => {
        if (keys.length === 1) {
          return i[keys[0]] === keyValue.value;
        } else if (keys.length === 2) {
          return i[keys[0]][keys[1]] === keyValue.value;
        } else if (keys.length === 3) {
          return i[keys[0]][keys[1]][keys[2]] === keyValue.value;
        } else {
          // Just add another ("keys.length === n)" if this happens
          console.error('InMemoryDatabaseBase - applyEqualsFilter - filterKeySplit.length is > 3');
        }
      });
    }

    return list;
  }

  applyGreaterThanFilter(filtersStr, list) {
    const keyValue = this.getGreaterThanValue(filtersStr);

    if (keyValue.key) {
      const keys = keyValue.key.split('.');

      list = list.filter((i: any) => {
        if (keys.length === 1) {
          return i[keys[0]] > keyValue.value;
        } else if (keys.length > 2) {
          return i[keys[0]][keys[1]] > keyValue.value;
        } else if (keys.length === 3) {
          return i[keys[0]][keys[1]][keys[2]] > keyValue.value;
        } else {
          // Just add another ("keys.length === n)" if this happens
          console.error('InMemoryDatabaseBase - applyGreaterThanFilter - filterKeySplit.length is > 3');
        }
      });
    }

    return list;
  }

  applyLessThanFilter(filtersStr, list) {
    const keyValue = this.getLessThanValue(filtersStr);

    if (keyValue.key) {
      const keys = keyValue.key.split('.');

      list = list.filter((i: any) => {
        if (keys.length === 1) {
          return i[keys[0]] < keyValue.value;
        } else if (keys.length < 2) {
          return i[keys[0]][keys[1]] < keyValue.value;
        } else if (keys.length === 3) {
          return i[keys[0]][keys[1]][keys[2]] < keyValue.value;
        } else {
          // Just add another ("keys.length === n)" if this happens
          console.error('InMemoryDatabaseBase - applyLessThanFilter - filterKeySplit.length is > 3');
        }
      });
    }

    return list;
  }

  applyFilters(url: string, list: any[]): any[] {
    const optionalUrlParams = this.getOptionalUrlParams(url);

    // Filter
    if (optionalUrlParams.filters.length > 0) {
      let filters = optionalUrlParams.filters.split(' AND ');

      if (optionalUrlParams.filters.indexOf(' AND ') === -1) {
        filters = optionalUrlParams.filters.split('%20AND%20');
      }

      filters.forEach((filterStr: string) => {
        const filterType = this.determineFilter(filterStr);

        if (filterType === 'equals') {
          list = this.applyEqualsFilter(filterStr, list);
        } else if (filterType === 'greaterThan') {
          list = this.applyGreaterThanFilter(filterStr, list);
        } else if (filterType === 'lessThan') {
          list = this.applyLessThanFilter(filterStr, list);
        }
      });
    }

    // Sort

    list = list.sort((a: any, b: any) => {
      if (a[optionalUrlParams.sort] < b[optionalUrlParams.sort]) {
        return (optionalUrlParams.order === Filter.SORT_ASC) ? -1 : 1;
      } else if (a[optionalUrlParams.sort] > b[optionalUrlParams.sort]) {
        return (optionalUrlParams.order === Filter.SORT_ASC) ? 1 : -1;
      } else {
        return 0;
      }
    });

    // Size & Pagination

    let startPos = optionalUrlParams.size * optionalUrlParams.offset;
    startPos = startPos < 0 ? 0 : startPos;

    if (startPos > list.length - 1) {
      list = [];
    } else {
      let endPos = startPos + optionalUrlParams.size;
      endPos = endPos > list.length ? list.length : endPos;
      list = list.slice(startPos, endPos);
    }

    return list;
  }
}
