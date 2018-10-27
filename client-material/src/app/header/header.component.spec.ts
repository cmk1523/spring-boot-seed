import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HeaderComponent } from './header.component';
import {Router} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {Location} from '@angular/common';
import {SharedComponentsModule} from '../shared/components/shared-components.module';
import {InMemoryDatabase} from '../shared/InMemoryDatabase';
import {MatDividerModule, MatIconModule, MatMenuModule, MatToolbarModule} from '@angular/material';
import {AppService} from '../shared/services/app.service';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;
  let router: Router;
  let location: Location;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        HeaderComponent
      ],
      providers: [
      ],
      imports: [
        SharedComponentsModule,
        RouterTestingModule.withRoutes([]),
        MatIconModule,
        MatMenuModule,
        MatToolbarModule,
        MatDividerModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    router = TestBed.get(Router);
    location = TestBed.get(Location);
    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    AppService.APP_INFO = InMemoryDatabase.GenerateAppInfoResponse().data;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
