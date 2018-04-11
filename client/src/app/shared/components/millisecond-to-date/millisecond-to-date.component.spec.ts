import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MillisecondToDateComponent } from './millisecond-to-date.component';

describe('MillisecondToDateComponent', () => {
  let component: MillisecondToDateComponent;
  let fixture: ComponentFixture<MillisecondToDateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MillisecondToDateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MillisecondToDateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
