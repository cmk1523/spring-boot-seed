import { Component, OnInit } from '@angular/core';
import {UserService} from '../../shared/services/User.service';
import {AppService} from '../../shared/services/App.service';
import {User} from '../../shared/objects/auditable/User';

declare let toastr: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  appInfo: any = AppService.APP_INFO;
  users: User[] = [];

  constructor(private userService: UserService,
              private appService: AppService) { }

  ngOnInit() {
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
