import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {NotEnabledComponent} from './not-enabled/not-enabled.component';
import {UnauthorizedComponent} from './unauthorized/unauthorized.component';
import {ErrorComponent} from './error/error.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {SharedComponentsModule} from '../shared/components/shared-components.module';

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    FormsModule,
    RouterModule,

    SharedComponentsModule,
  ],
  providers: [
  ],
  declarations: [
    ErrorComponent,
    UnauthorizedComponent,
    NotEnabledComponent,
    PageNotFoundComponent,
  ],
  exports: [
    ErrorComponent,
    UnauthorizedComponent,
    NotEnabledComponent,
    PageNotFoundComponent,
  ]
})
export class InvalidModule { }
