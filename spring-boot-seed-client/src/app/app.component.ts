import {Component, OnInit} from '@angular/core';
import {AppService} from './shared/services/App.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  loading = true;
  loadingMessage = 'Loading application information...';

  constructor(private appService: AppService) { }

  ngOnInit(): void {
    const TRANSITION_TIME = 3000;
    const startTime = new Date().getTime();

    const subscription = this.appService.appInfo.subscribe(
      () => {
        let diff = new Date().getTime() - startTime;

        setTimeout(() => {
          this.loading = false;
        }, (diff < TRANSITION_TIME) ? TRANSITION_TIME - diff : 1);
      }, () => {
      }, () => {
        subscription.unsubscribe();
      });
  }
}
