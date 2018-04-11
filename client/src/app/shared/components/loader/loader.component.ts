import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {BaseService} from '../../services/base.service';
import {EventService} from '../../services/event.service';

@Component({
  selector: 'app-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.css']
})
export class LoaderComponent implements OnInit, OnChanges {
  @Input()
  loading = false;
  counter = 0;

  constructor(private eventService: EventService) { }

  ngOnInit() {
    this.eventService.loading.subscribe((load: any) => {
      // console.log('LoaderComponent - loading event');

      if (load === 'kill') {
        this.counter = 0;
      } else if (load === true) {
        this.counter++;
      } else {
        this.counter--;
      }

      this.loading = this.counter > 0;
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    const change = changes['loading'];
    const curVal  = JSON.stringify(change.currentValue);
    const prevVal = JSON.stringify(change.previousValue);

    if (BaseService.IsDifferent(curVal, prevVal)) {
      this.loading = change.currentValue;
    }
  }

}
