import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {BaseAngularComponent} from '../../../shared/components/base-angular/base-angular.component';
import {User} from '../../../shared/objects/auditable/User';
import {BaseService} from '../../../shared/services/base.service';

import * as moment from 'moment';
import {MillisecondToDate} from '../../../shared/pipes/MillisecondToDate.pipe';
import {PreferenceService} from '../../../shared/services/preference.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent extends BaseAngularComponent implements OnInit, OnChanges {
  @Input()
  user: User = null;
  @Input()
  showDisabledFields = true;

  constructor() {
    super();
  }

  ngOnInit() {
    if (this.user == null) {
      console.error('UserFormComponent - user is null');
    }

    this.initializeUserForDisplay();
  }

  ngOnChanges(changes: SimpleChanges): void {
    const change = changes['user'];
    const curVal  = JSON.stringify(change.currentValue);
    const prevVal = JSON.stringify(change.previousValue);

    if (BaseService.IsDifferent(curVal, prevVal)) {
      this.user = change.currentValue;
      this.initializeUserForDisplay();
    }
  }

  initializeUserForDisplay() {
    this.user['createdDateStr'] = moment(this.user.createdDate).format(MillisecondToDate.MMDDYYYY_HH_MM_SS).toUpperCase();
    this.user['updatedDateStr'] = moment(this.user.updatedDate).format(MillisecondToDate.MMDDYYYY_HH_MM_SS).toUpperCase();
  }

  readOnlyFieldsBtnClicked() {
    this.showDisabledFields = !this.showDisabledFields;
    PreferenceService.USER_FORM_SHOW_READONLY_FIELDS = this.showDisabledFields;
  }
}
