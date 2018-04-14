import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserComponent } from './user.component';
import {SharedComponentsModule} from '../../../shared/components/shared-components.module';
import {RouterTestingModule} from '@angular/router/testing';
import {InMemoryDatabase} from '../../../shared/InMemoryDatabase';
import {UsersModule} from '../users.module';
import {HttpClientModule} from '@angular/common/http';
import {EventService} from '../../../shared/services/event.service';
import {Location} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import 'rxjs/add/observable/of';

describe('UserComponent', () => {
  let component: UserComponent;
  let fixture: ComponentFixture<UserComponent>;
  let router: Router;
  let location: Location;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
      ],
      providers: [
        EventService
      ],
      imports: [
        SharedComponentsModule,
        UsersModule,
        HttpClientModule,
        RouterTestingModule.withRoutes([]),
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    router = TestBed.get(Router);
    location = TestBed.get(Location);
    fixture = TestBed.createComponent(UserComponent);
    component = fixture.componentInstance;
    component.appInfo = InMemoryDatabase.GenerateAppInfoResponse().data;
    component.user = InMemoryDatabase.GenerateUsersResponse().data[0];
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
