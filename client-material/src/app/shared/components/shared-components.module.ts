import { NgModule } from '@angular/core';

import {RouterModule} from '@angular/router';
import {BrowserModule} from '@angular/platform-browser';
import {MillisecondToDateComponent} from './millisecond-to-date/millisecond-to-date.component';
import {PipesModule} from '../pipes/pipes.module';
import {BaseAngularComponent} from './base-angular/base-angular.component';
import {LoaderComponent} from './loader/loader.component';
import {
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatProgressBarModule,
  MatProgressSpinnerModule
} from '@angular/material';
import { CurrencyInputComponent } from './currency-input/currency-input.component';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    BaseAngularComponent,
    MillisecondToDateComponent,
    LoaderComponent,
    CurrencyInputComponent
  ],
  exports: [
    BaseAngularComponent,
    MillisecondToDateComponent,
    LoaderComponent,
    CurrencyInputComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    FormsModule,

    PipesModule,

    MatProgressBarModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatProgressSpinnerModule,
  ],
  providers: [

  ],
})
export class SharedComponentsModule { }
