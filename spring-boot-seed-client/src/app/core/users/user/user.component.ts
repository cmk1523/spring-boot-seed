import { Component, OnInit } from '@angular/core';
import {BaseAngularComponent} from '../../../shared/components/base-angular/base-angular.component';
import {User} from '../../../shared/objects/auditable/User';
import {ActivatedRoute, ActivatedRouteSnapshot, Router} from '@angular/router';
import {UserService} from '../../../shared/services/user.service';
import {PreferenceService} from '../../../shared/services/preference.service';

declare let toastr: any;

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent extends BaseAngularComponent implements OnInit {
  user: User = null;
  showDisabledFields = PreferenceService.USER_FORM_SHOW_READONLY_FIELDS;

  constructor(protected router: Router, protected route: ActivatedRoute, protected userService: UserService) {
    super();
  }

  ngOnInit() {
    if (this.route.snapshot.data['data']) {
      this.user = this.route.snapshot.data['data'];
    }
  }

  deleteBtnClicked() {
    if (confirm('Are you sure you want to delete this user?')) {
      const subscription = this.userService.delete(this.user.id).subscribe(() => {
        toastr.success('Deleted Successfully');
      }, () => {
        toastr.error('Unable to delete user');
      }, () => {
        setTimeout(() => {
          this.router.navigate(['/users']);
        }, 100);
        subscription.unsubscribe();
      });
    }
  }

  saveBtnClicked() {
    this.user.updatedBy = this.appInfo.user.username;
    this.user.updatedDate = new Date().getTime();
    const vr = User.Validate(this.user);

    if (!vr.isValid) {
      toastr.warning('Unable to update user. Invalid user: ' + vr.message);
    } else {
      const subscription = this.userService.update(this.user).subscribe((user: User) => {
        this.user = user;
        toastr.success('Saved Successfully');
      }, () => {
        toastr.error('Unable to save user');
      }, () => {
        subscription.unsubscribe();
      });
    }
  }

  cancelBtnClicked() {

  }
}
