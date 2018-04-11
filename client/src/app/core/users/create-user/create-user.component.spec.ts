import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import {SharedComponentsModule} from '../../../shared/components/shared-components.module';
import {RouterTestingModule} from '@angular/router/testing';
import {InMemoryDatabase} from '../../../shared/InMemoryDatabase';
import {CreateUserComponent} from './create-user.component';
import {UsersModule} from '../users.module';
import {HttpClientModule} from '@angular/common/http';
import {EventService} from '../../../shared/services/event.service';

describe('CreateUserComponent', () => {
  let component: CreateUserComponent;
  let fixture: ComponentFixture<CreateUserComponent>;

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
    fixture = TestBed.createComponent(CreateUserComponent);
    component = fixture.componentInstance;
    component.appInfo = InMemoryDatabase.APP_INFO_TEST.data;
    component.user = InMemoryDatabase.GenerateDefaultUsers().data[0];
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
