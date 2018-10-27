import { TestBed, inject } from '@angular/core/testing';

import { FileService } from './file.service';
import {MatSnackBarModule} from '@angular/material';
import {HttpClientModule} from '@angular/common/http';
import {EventService} from './event.service';

describe('FileService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        FileService,
        EventService
      ],
      imports: [
        HttpClientModule,
        MatSnackBarModule
      ]
    });
  });

  it('should be created', inject([FileService], (service: FileService) => {
    expect(service).toBeTruthy();
  }));
});
