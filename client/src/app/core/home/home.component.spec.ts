import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home.component';
import {Router} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {Location} from '@angular/common';
import {SharedComponentsModule} from '../../shared/components/shared-components.module';
import {UserService} from '../../shared/services/user.service';
import {HttpClientModule} from '@angular/common/http';
import {AppService} from '../../shared/services/App.service';
import {InMemoryDatabase} from '../../shared/InMemoryDatabase';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let router: Router;
  let location: Location;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        HomeComponent,
      ],
      providers: [
        UserService,
        AppService
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
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    component.appInfo = InMemoryDatabase.GenerateAppInfoResponse().data;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
