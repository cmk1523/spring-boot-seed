import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import {RouterTestingModule} from '@angular/router/testing';
import {InMemoryDatabase} from '../shared/InMemoryDatabase';
import {SharedComponentsModule} from '../shared/components/shared-components.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatTableModule} from '@angular/material';
import {AppService} from '../shared/services/app.service';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        HomeComponent
      ],
      imports: [
        SharedComponentsModule,
        RouterTestingModule.withRoutes([]),
        BrowserAnimationsModule,
        MatTableModule
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    AppService.APP_INFO = InMemoryDatabase.GenerateAppInfoResponse().data;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
