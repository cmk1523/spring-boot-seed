import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NotEnabledComponent } from './not-enabled.component';
import {SharedComponentsModule} from '../../shared/components/shared-components.module';
import {RouterTestingModule} from '@angular/router/testing';
import {InMemoryDatabase} from '../../shared/InMemoryDatabase';
import {AppService} from '../../shared/services/app.service';

describe('NotEnabledComponent', () => {
  let component: NotEnabledComponent;
  let fixture: ComponentFixture<NotEnabledComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        NotEnabledComponent
      ],
      imports: [
        SharedComponentsModule,
        RouterTestingModule.withRoutes([]),
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NotEnabledComponent);
    component = fixture.componentInstance;
    AppService.APP_INFO = InMemoryDatabase.GenerateAppInfoResponse().data;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
