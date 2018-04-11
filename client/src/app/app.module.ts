import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HomeComponent } from './core/home/home.component';
import {PreloadAllModules, RouterModule} from '@angular/router';
import {routes} from './app.routing';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import {UserService} from './shared/services/user.service';
import {FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {SharedComponentsModule} from './shared/components/shared-components.module';
import {AppService} from './shared/services/App.service';
import {TestInterceptor} from './shared/InMemoryDatabaseInterceptor';
import {BaseResolver} from './shared/resolvers/BaseResolver';
import {UsersResolver} from './shared/resolvers/UsersResolver';
import {UsersModule} from './core/users/users.module';
import { UserInfoComponent } from './core/user-info/user-info.component';
import { AppInfoComponent } from './core/app-info/app-info.component';
import {HttpClientInMemoryWebApiModule} from 'angular-in-memory-web-api';
import {InMemoryDatabase} from './shared/InMemoryDatabase';
import {BaseService} from './shared/services/base.service';
import {EventService} from './shared/services/event.service';
import {UserResolver} from './shared/resolvers/UserResolver';
import {PreferenceService} from './shared/services/preference.service';
import {UtilitiesService} from './shared/services/utilities.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    PageNotFoundComponent,
    UserInfoComponent,
    AppInfoComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,

    SharedComponentsModule,
    UsersModule,

    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules }),

    // HttpClientInMemoryWebApiModule.forRoot(
    //   InMemoryDatabase, { delay: 750, apiBase: 'api/v1/', dataEncapsulation: false }
    // )
  ],
  providers: [
    BaseService,
    AppService,
    UserService,
    EventService,
    PreferenceService,
    UtilitiesService,

    BaseResolver,
    UsersResolver,
    UserResolver,

    { provide: HTTP_INTERCEPTORS, useClass: TestInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
