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
  loadingAppAndUserInfo = true;
  loadingMessage = 'Loading application information...';

  constructor(private appService: AppService) { }

  ngOnInit(): void {
    this.initToastr();

    const TRANSITION_TIME = 3000;
    const startTime = new Date().getTime();

    const subscription = this.appService.appInfo.subscribe(
      () => {
        this.loadingAppAndUserInfo = false;
        let diff = new Date().getTime() - startTime;

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
