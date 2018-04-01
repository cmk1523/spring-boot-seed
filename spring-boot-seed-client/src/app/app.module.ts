import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import {PreloadAllModules, RouterModule} from '@angular/router';
import {routes} from './app.routing';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import {UserService} from './shared/services/User.service';
import {FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {SharedComponentsModule} from './shared/components/shared-components.module';
import {AppService} from './shared/services/App.service';
import {HttpClientInMemoryWebApiModule} from 'angular-in-memory-web-api';
import {InMemoryDatabase} from './shared/InMemoryDatabase';
import {TestInterceptor} from './shared/InMemoryDatabaseInterceptor';
import {BaseResolver} from './shared/resolvers/BaseResolver';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    PageNotFoundComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,

    SharedComponentsModule,

    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules }),

    // HttpClientInMemoryWebApiModule.forRoot(
    //   InMemoryDatabase, { delay: 1000, apiBase: 'api/v1/', dataEncapsulation: false }
    // )
  ],
  providers: [
    AppService,
    UserService,

    BaseResolver,

    { provide: HTTP_INTERCEPTORS, useClass: TestInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
