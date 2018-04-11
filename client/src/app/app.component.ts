import {Component, OnInit} from '@angular/core';
import {AppService} from './shared/services/App.service';
import {BaseAngularComponent} from './shared/components/base-angular/base-angular.component';
import {ToastrConfig} from './shared/config/ToastrConfig';

declare let toastr: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent extends BaseAngularComponent implements OnInit {
  initializing = true;
  loading = true;

  constructor(protected appService: AppService) {
    super();
  }

  ngOnInit(): void {
    ToastrConfig.InitToastr();
    const TRANSITION_TIME = 3000;
    const startTime = new Date().getTime();

    // Note: appInfo is a replay subject and will fire whenever the service gets data
    const subscription = this.appService.appInfo.subscribe(
      (appInfo: any) => {
        this.appInfo = appInfo;
        this.loading = false;
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
