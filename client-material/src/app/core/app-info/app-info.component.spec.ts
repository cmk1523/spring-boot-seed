import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { AppInfoComponent } from './app-info.component';
import {SharedComponentsModule} from '../../shared/components/shared-components.module';
import {RouterTestingModule} from '@angular/router/testing';
import {InMemoryDatabase} from '../../shared/InMemoryDatabase';
import {AppService} from '../../shared/services/app.service';
import {CoreModule} from '../core.module';

describe('AppInfoComponent', () => {
  let component: AppInfoComponent;
  let fixture: ComponentFixture<AppInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
      ],
      imports: [
        SharedComponentsModule,
        RouterTestingModule.withRoutes([]),

        CoreModule,
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppInfoComponent);
    component = fixture.componentInstance;
    AppService.APP_INFO = InMemoryDatabase.GenerateAppInfoResponse().data;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
