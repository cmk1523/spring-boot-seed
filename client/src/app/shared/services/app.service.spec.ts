import { TestBed, inject } from '@angular/core/testing';

import { AppService } from './App.service';
import {HttpClientModule} from '@angular/common/http';
import {EventService} from './event.service';

describe('AppService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        AppService,
        EventService
      ],
      imports: [
        HttpClientModule
      ]
    });
  });

  it('should be created', inject([AppService], (service: AppService) => {
    expect(service).toBeTruthy();
  }));
});
