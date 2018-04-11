import { Injectable } from '@angular/core';

@Injectable()
export class UtilitiesService {

  public static GetUrlParams(url: string): string {
    if (url == null || url.indexOf('?') === -1) { return ''; }
    const split = url.split('?');
    return (split.length > 1) ? split[1] : '';
  }

  public static GetUrlParam(url: string, keyToFind: string): any {
    const paramStr = UtilitiesService.GetUrlParams(url);
    if (paramStr == null || paramStr.length === 0) { return ''; }
    const params = paramStr.split('&');

    for (let i = 0; i < params.length; i++) {
      const keyValues = params[i].split('=');
      if (keyValues.length === 2 && keyValues[0] === keyToFind) { return keyValues[1]; }
    }

    return null;
  }

  constructor() { }

}
