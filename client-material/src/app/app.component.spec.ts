import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {AppComponent} from './app.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {AppService} from './shared/services/app.service';
import {SharedComponentsModule} from './shared/components/shared-components.module';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientModule} from '@angular/common/http';
import {EventService} from './shared/services/event.service';
import {MatSnackBar, MatSnackBarModule} from '@angular/material';
import {Overlay} from '@angular/cdk/overlay';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
      providers: [
        EventService,
        AppService,
      ],
      declarations: [
        AppComponent
      ],
      imports: [
        SharedComponentsModule,
        HttpClientModule,
        RouterTestingModule.withRoutes([]),
        MatSnackBarModule
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
