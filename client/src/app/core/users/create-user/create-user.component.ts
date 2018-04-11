import { Component, OnInit } from '@angular/core';
import {BaseAngularComponent} from '../../../shared/components/base-angular/base-angular.component';
import {User} from '../../../shared/objects/auditable/User';
import {Router} from '@angular/router';
import {UserService} from '../../../shared/services/user.service';

declare let toastr: any;

@Component({
  selector: 'app-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent extends BaseAngularComponent implements OnInit {
  user: User = new User();

  constructor(protected router: Router, protected userService: UserService) {
    super();
  }

  ngOnInit() {
    this.user.createdBy = this.appInfo.user.username;
    this.user.createdDate = new Date().getTime();
    this.user.updatedBy = this.user.createdBy;
    this.user.updatedDate = this.user.createdDate;
  }

  createBtnClicked() {
    const vr = User.Validate(this.user, true);

    if (!vr.isValid) {
      toastr.warning('Unable to create user. Invalid user: ' + vr.message);
    } else {
      const subscription = this.userService.create(this.user).subscribe((user: User) => {
        this.user = user;
        toastr.success('Created Successfully');

        setTimeout(() => {
          this.router.navigate(['/users', this.user.id]);
        }, 100);
      }, () => {
        toastr.error('Unable to create user');
      }, () => {
        setTimeout(() => {
          this.router.navigate(['/users']);
        }, 1000);
        subscription.unsubscribe();
      });
    }
  }

  cancelBtnClicked() {
    this.router.navigate(['/users']);
  }
}
