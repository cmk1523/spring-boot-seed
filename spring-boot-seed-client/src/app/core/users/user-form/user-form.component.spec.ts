import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserFormComponent } from './user-form.component';
import {SharedComponentsModule} from '../../../shared/components/shared-components.module';
import {RouterTestingModule} from '@angular/router/testing';
import {InMemoryDatabase} from '../../../shared/InMemoryDatabase';

describe('UserFormComponent', () => {
  let component: UserFormComponent;
  let fixture: ComponentFixture<UserFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        UserFormComponent
      ],
      imports: [
        SharedComponentsModule,
        RouterTestingModule.withRoutes([]),
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserFormComponent);
    component = fixture.componentInstance;
    component.appInfo = InMemoryDatabase.APP_INFO.data;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
