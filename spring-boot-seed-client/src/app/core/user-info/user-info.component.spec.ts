import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserInfoComponent } from './user-info.component';
import {SharedComponentsModule} from '../../shared/components/shared-components.module';
import {RouterTestingModule} from '@angular/router/testing';
import {InMemoryDatabase} from '../../shared/InMemoryDatabase';
import {HttpClientModule} from '@angular/common/http';
import {Location} from '@angular/common';
import {Router} from '@angular/router';

describe('UserInfoComponent', () => {
  let component: UserInfoComponent;
  let fixture: ComponentFixture<UserInfoComponent>;
  let router: Router;
  let location: Location;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        UserInfoComponent
      ],
      providers: [
      ],
      imports: [
        SharedComponentsModule,
        HttpClientModule,
        RouterTestingModule.withRoutes([]),
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    router = TestBed.get(Router);
    location = TestBed.get(Location);
    fixture = TestBed.createComponent(UserInfoComponent);
    component = fixture.componentInstance;
    component.appInfo = InMemoryDatabase.APP_INFO.data;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
