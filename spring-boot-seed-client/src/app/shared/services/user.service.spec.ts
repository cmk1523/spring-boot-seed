import { TestBed, inject } from '@angular/core/testing';

import { UserService } from './User.service';
import {HttpClientModule} from '@angular/common/http';
import {EventService} from './event.service';

describe('UserService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        UserService,
        EventService
      ],
      imports: [
        HttpClientModule
      ]
    });
  });

  it('should be created', inject([UserService], (service: UserService) => {
    expect(service).toBeTruthy();
  }));
});
