import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {SharedComponentsModule} from '../shared/components/shared-components.module';
import {MatFormFieldModule, MatInputModule, MatSnackBarModule} from '@angular/material';
import {AppInfoComponent} from './app-info/app-info.component';
import {UserInfoComponent} from './user-info/user-info.component';

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    FormsModule,
    RouterModule,

    SharedComponentsModule,
  ],
  providers: [
    MatSnackBarModule,
    MatInputModule,
    MatFormFieldModule,
  ],
  declarations: [
    AppInfoComponent,
    UserInfoComponent
  ],
  exports: [
    AppInfoComponent,
    UserInfoComponent
  ]
})
export class CoreModule { }
