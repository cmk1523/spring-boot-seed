import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import {HttpModule} from '@angular/http';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {MillisecondToDate} from './MillisecondToDate.pipe';

@NgModule({
  imports:      [
    BrowserModule,
    FormsModule,
    RouterModule,
  ],
  declarations: [
    MillisecondToDate,
  ],
  exports: [
    MillisecondToDate
  ],
  providers: [

  ],
  bootstrap: [

  ]
})
export class PipesModule {

}
