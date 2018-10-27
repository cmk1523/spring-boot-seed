import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrencyInputComponent } from './currency-input.component';
import {SharedComponentsModule} from '../shared-components.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

describe('CurrencyInputComponent', () => {
  let component: CurrencyInputComponent;
  let fixture: ComponentFixture<CurrencyInputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
      ],
      imports: [
        SharedComponentsModule,
        BrowserAnimationsModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CurrencyInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
