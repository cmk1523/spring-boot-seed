import { TestBed, inject } from '@angular/core/testing';

import { AppService } from './app.service';
import {HttpClientModule} from '@angular/common/http';
import {EventService} from './event.service';
import {MatSnackBarModule} from '@angular/material';

describe('AppService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        AppService,
        EventService
      ],
      imports: [
        HttpClientModule,
        MatSnackBarModule
      ]
    });
  });

  it('should be created', inject([AppService], (service: AppService) => {
    expect(service).toBeTruthy();
  }));
});
