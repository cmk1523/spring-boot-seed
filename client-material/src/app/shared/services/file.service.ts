import {Injectable} from '@angular/core';
import {EventService} from './event.service';
import {MatSnackBar} from '@angular/material';
import {BaseService} from './base.service';
import {HttpClient} from '@angular/common/http';
import {GenericFile} from '../objects/auditable/GenericFile';
import * as CryptoJS from 'crypto-js';
import {AppService} from './app.service';
import {Observable} from 'rxjs/index';

@Injectable()
export class FileService extends BaseService {
  private filesUrl = this.baseUrl + 'api/v1/files';

  constructor(protected http: HttpClient, protected eventService: EventService, protected snackBar: MatSnackBar) {
    super(http, eventService, snackBar);
  }

  handleFileInput(files: FileList): Observable<GenericFile[]> {
    return new Observable((observer) => {
      let numberOfFilesLoaded = 0;
      const genericFiles: GenericFile[] = [];

      for (let i = 0; i < files.length; i++) {
        const file: File = files.item(i);
        const fileReader = new FileReader();

        fileReader.onload = (fileLoadedEvent: any) => {
          numberOfFilesLoaded++;

          if (fileLoadedEvent.target.result) {
            const split = fileLoadedEvent.target.result.split(';base64,');
            const md5 = CryptoJS.MD5(fileLoadedEvent.target.result).toString(CryptoJS.enc.Hex);

            if (split.length === 2) {
              const base64 = fileLoadedEvent.target.result.split(',')[1];

              const newItem = new GenericFile({
                id: md5,
                name: file.name,
                createdBy: AppService.APP_INFO.user.username,
                createdDate: new Date().getTime(),
                updatedBy: AppService.APP_INFO.user.username,
                updatedDate: new Date().getTime(),
                removed: false,
                md5: md5,
                size: file.size,
                type: file.type,
                base64: base64,
                path: 'data:' + file.type + ';base64,' + base64,
                lastModified: file.lastModified
              });

              genericFiles.push(newItem);

              if (files.length === numberOfFilesLoaded) {
                observer.next(genericFiles);
                observer.complete();
              }
            } else {
              console.error('ExpenseFormComponent - handleFileInput - Unable to split');
              observer.error();
            }
          } else {
            console.error('ExpenseFormComponent - handleFileInput - Invalid file');
            observer.error();
          }
        };

        fileReader.readAsDataURL(file);
      }
    });
  }
}
