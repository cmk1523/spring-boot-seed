import {Component, OnInit} from '@angular/core';
import {AppService} from './shared/services/app.service';
import {BaseAngularComponent} from './shared/components/base-angular/base-angular.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent extends BaseAngularComponent implements OnInit {
  initializing = true;

  constructor(protected appService: AppService) {
    super();
  }

  ngOnInit(): void {
    const TRANSITION_TIME = 1500;
    const startTime = new Date().getTime();

    // Note: appInfo is a replay subject and will fire whenever the service gets data
    const subscription = this.appService.appInfo.subscribe(
      (appInfo: any) => {
        this.appInfo = appInfo;
        document.title = this.appInfo.title;
        const diff = new Date().getTime() - startTime;

        // This ensures the loading message will last 3 seconds long
        setTimeout(() => {
          this.initializing = false;
        }, (diff < TRANSITION_TIME) ? TRANSITION_TIME - diff : 1);
      }, () => {
      }, () => {
        subscription.unsubscribe();
      });
  }
}
