import { Component, OnInit } from '@angular/core';
import {User} from '../../../shared/objects/auditable/User';
import {ActivatedRoute} from '@angular/router';
import {BaseAngularComponent} from '../../../shared/components/base-angular/base-angular.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent extends BaseAngularComponent implements OnInit {
  users: User[] = [];

  constructor(protected route: ActivatedRoute) {
    super();
  }

  ngOnInit() {
    this.users = this.route.snapshot.data['users'];
  }

}
