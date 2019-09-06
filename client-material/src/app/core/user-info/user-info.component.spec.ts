import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserInfoComponent } from './user-info.component';
import {SharedComponentsModule} from '../../shared/components/shared-components.module';
import {RouterTestingModule} from '@angular/router/testing';
import {InMemoryDatabase} from '../../shared/InMemoryDatabase';
import {HttpClientModule} from '@angular/common/http';
import {Location} from '@angular/common';
import {Router} from '@angular/router';
import {EventService} from '../../shared/services/event.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppService} from '../../shared/services/app.service';
import {CoreModule} from '../core.module';

describe('UserInfoComponent', () => {
  let component: UserInfoComponent;
  let fixture: ComponentFixture<UserInfoComponent>;
  let router: Router;
  let location: Location;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
      ],
      providers: [
        EventService,
      ],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        SharedComponentsModule,
        HttpClientModule,
        RouterTestingModule.withRoutes([]),
        BrowserAnimationsModule,

        CoreModule,
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    router = TestBed.get(Router);
    location = TestBed.get(Location);
    fixture = TestBed.createComponent(UserInfoComponent);
    component = fixture.componentInstance;
    AppService.APP_INFO = InMemoryDatabase.GenerateAppInfoResponse().data;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
