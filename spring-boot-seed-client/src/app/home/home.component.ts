import { Component, OnInit } from '@angular/core';
import {UserService} from '../shared/services/User.service';
import {AppService} from '../shared/services/App.service';
import {User} from '../shared/objects/auditable/User';

declare let toastr: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  appInfo: any = {};
  users: User[] = [];

  constructor(private userService: UserService,
              private appService: AppService) { }

  ngOnInit() {
    const subscription = this.appService.getAppInfo().subscribe(
      (appInfo: any) => {
        toastr.success('Successfully retrieved application info');
        this.appInfo = appInfo;
      }, () => {
      }, () => {
        subscription.unsubscribe();
      }, );

    const subscription2 = this.userService.getAll().subscribe(
      (users: User[]) => {
        toastr.success('Successfully retrieved all users');
        this.users = users;
      }, () => {
      }, () => {
        subscription2.unsubscribe();
      }, );
  }

}
