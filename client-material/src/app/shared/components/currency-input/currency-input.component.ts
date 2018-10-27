import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {BaseService} from '../../services/base.service';
import {MillisecondToDate} from '../../pipes/MillisecondToDate.pipe';

@Component({
  selector: 'app-currency-input',
  templateUrl: './currency-input.component.html',
  styleUrls: ['./currency-input.component.scss']
})
export class CurrencyInputComponent implements OnInit, OnChanges {
  @Input()
  formName = '';
  @Input()
  value = 0;
  @Output()
  valueChange = new EventEmitter<number>();
  @Input()
  field = '';
  @Input()
  name = '';
  @Input()
  id = '';
  @Input()
  placeholder = '';
  @Input()
  required = true;

  valueAsStr = '';

  constructor() {
  }

  ngOnInit() {
    if (this.value != null) {
      this.initializeForDisplay();
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    const change = changes['value'];

    if (change && change.currentValue != null) {
      const curVal = JSON.stringify(change.currentValue);
      const prevVal = JSON.stringify(change.previousValue);

      if (BaseService.IsDifferent(curVal, prevVal)) {
        this.value = change.currentValue;
        this.initializeForDisplay();
      }
    }
  }

  initializeForDisplay() {
    this.valueAsStr = this.round(this.value, 2).toFixed(2);
    this.value = parseFloat(this.valueAsStr);
  }

  valueChanged(value: number) {
    const valueAsStr = this.round(value, 2).toFixed(2);
    value = parseFloat(valueAsStr);
    this.valueChange.emit(value);
  }

  round(value, precision) {
    const multiplier = Math.pow(10, precision || 0);
    return Math.round(value * multiplier) / multiplier;
  }
}
