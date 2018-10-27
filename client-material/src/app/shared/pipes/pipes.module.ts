import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {MillisecondToDate} from './MillisecondToDate.pipe';
import {FilesizePipe} from './filesize.pipe';

@NgModule({
  imports:      [
    BrowserModule,
    FormsModule,
    RouterModule,
  ],
  declarations: [
    MillisecondToDate,
    FilesizePipe,
  ],
  exports: [
    MillisecondToDate,
    FilesizePipe,
  ],
  providers: [

  ],
  bootstrap: [

  ]
})
export class PipesModule {

}
