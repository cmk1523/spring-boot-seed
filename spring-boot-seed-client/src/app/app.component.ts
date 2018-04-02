import {Component, OnInit} from '@angular/core';
import {AppService} from './shared/services/App.service';

declare let toastr: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  loading = true;
  loadingData = true;
  appInfo: any = null;

  constructor(private appService: AppService) { }

  ngOnInit(): void {
    this.initToastr();
    const TRANSITION_TIME = 3000;
    const startTime = new Date().getTime();

    // Note: appInfo is a replay subject and will fire whenever the service gets data
    const subscription = this.appService.appInfo.subscribe(
      (appInfo: any) => {
        this.appInfo = appInfo;
        this.loadingData = false;
        let diff = new Date().getTime() - startTime;

        // This ensures the loading message will last 3 seconds long
        setTimeout(() => {
          this.loading = false;
        }, (diff < TRANSITION_TIME) ? TRANSITION_TIME - diff : 1);
      }, () => {
      }, () => {
        subscription.unsubscribe();
      });
  }

  public initToastr() {
    toastr.options = {
      "closeButton": false,
      "debug": false,
      "newestOnTop": false,
      "progressBar": false,
      "positionClass": "toast-bottom-center",
      "preventDuplicates": false,
      "onclick": null,
      "showDuration": "300",
      "hideDuration": "1000",
      "timeOut": "5000",
      "extendedTimeOut": "1000",
      "showEasing": "swing",
      "hideEasing": "linear",
      "showMethod": "fadeIn",
      "hideMethod": "fadeOut"
    }
  }
}
