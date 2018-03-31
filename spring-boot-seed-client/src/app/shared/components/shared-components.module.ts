import { NgModule } from '@angular/core';

import {RouterModule} from '@angular/router';
import {BrowserModule} from '@angular/platform-browser';
import {MillisecondToDateComponent} from './millisecond-to-date/millisecond-to-date.component';
import {PipesModule} from '../pipes/pipes.module';

@NgModule({
  declarations: [
    MillisecondToDateComponent
  ],
  exports: [
    MillisecondToDateComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,

    PipesModule
  ],
  providers: [

  ],
})
export class SharedComponentsModule { }
