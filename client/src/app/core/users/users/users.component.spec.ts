import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersComponent } from './users.component';
import {SharedComponentsModule} from '../../../shared/components/shared-components.module';
import {RouterTestingModule} from '@angular/router/testing';
import {InMemoryDatabase} from '../../../shared/InMemoryDatabase';
import {UsersModule} from '../users.module';

describe('UsersComponent', () => {
  let component: UsersComponent;
  let fixture: ComponentFixture<UsersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
      ],
      imports: [
        SharedComponentsModule,
        UsersModule,
        RouterTestingModule.withRoutes([]),
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersComponent);
    component = fixture.componentInstance;
    component.appInfo = InMemoryDatabase.GenerateAppInfoResponse().data;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
