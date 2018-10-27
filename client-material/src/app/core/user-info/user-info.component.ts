import { Component, OnInit } from '@angular/core';
import {BaseAngularComponent} from '../../shared/components/base-angular/base-angular.component';
import {User} from '../../shared/objects/auditable/User';
import {ActivatedRoute, Router} from '@angular/router';
import {MatSnackBar} from '@angular/material';
import {Role} from '../../shared/objects/auditable/Role';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent extends BaseAngularComponent implements OnInit {
  user: User = null;
  roles: Role[] = null;

  constructor(protected router: Router, protected route: ActivatedRoute, protected snackBar: MatSnackBar) {
    super();
  }

  ngOnInit() {
    super.ngOnInit();

    if (this.user == null && this.roles == null && this.route.snapshot.data['data']) {
      this.user = this.route.snapshot.data['data'].user;
      this.roles = this.route.snapshot.data['data'].roles;
    }
  }

  saveBtnClicked() {
    this.user.updatedBy = this.appInfo.user.username;
    this.user.updatedDate = new Date().getTime();
    const vr = User.Validate(this.user);

    if (!vr.isValid) {
      this.snackBar.open('Invalid user: ' + vr.message, 'Validation',
        { duration: this.snackBarTimeout } );
    } else {
      // const subscription = this.userService.update(this.user).subscribe((user: User) => {
      //   this.user = user;
      //   this.snackBar.open('Updated user successfully', 'Update',
      //     { duration: this.snackBarTimeout } );
      // }, () => {
      //   this.snackBar.open('Unable to update user', 'Update',
      //     { duration: this.snackBarTimeout } );
      // }, () => {
      //   setTimeout(() => {
      //     this.router.navigate(['/admin', 'users']);
      //   }, 1000);
      //   subscription.unsubscribe();
      // });
    }
  }

}
