import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import {PreloadAllModules, RouterModule} from '@angular/router';
import {routes} from './app.routing';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {SharedComponentsModule} from './shared/components/shared-components.module';
import {AppService} from './shared/services/app.service';
import {BaseResolver} from './shared/resolvers/BaseResolver';
import {HttpClientInMemoryWebApiModule} from 'angular-in-memory-web-api';
import {InMemoryDatabase} from './shared/InMemoryDatabase';
import {BaseService} from './shared/services/base.service';
import {EventService} from './shared/services/event.service';
import {PreferenceService} from './shared/services/preference.service';
import {UtilitiesService} from './shared/services/utilities.service';
import {environment} from '../environments/environment';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatCardModule, MatDatepickerModule,
  MatDividerModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule, MatNativeDateModule, MatProgressSpinnerModule,
  MatSelectModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule,
  MatToolbarModule
} from '@angular/material';
import { HomeComponent } from './home/home.component';
import {HomeResolver} from './shared/resolvers/HomeResolver';
import {NotEnabledResolver} from './shared/resolvers/NotEnabledResolver';
import {InvalidModule} from './invalid/invalid.module';
import {TestInterceptor} from '../../../client/src/app/shared/InMemoryDatabaseInterceptor';
import {CoreModule} from './core/core.module';

// console.log('environment: ', environment);

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,

    SharedComponentsModule,
    InvalidModule,
    CoreModule,

    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules }),

    environment.inmemory ? HttpClientInMemoryWebApiModule.forRoot(
      InMemoryDatabase, { delay: 500, apiBase: 'api/v1/', dataEncapsulation: false }
    ) : [],

    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,

    MatToolbarModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    MatDividerModule,
    MatGridListModule,
    MatListModule,
    MatCardModule,
    MatTableModule,
    MatSortModule,
    MatInputModule,
    MatFormFieldModule,
    MatSnackBarModule,
    MatSelectModule,
    MatAutocompleteModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatProgressSpinnerModule
  ],
  providers: [
    BaseService,
    AppService,
    EventService,
    PreferenceService,
    UtilitiesService,

    BaseResolver,
    HomeResolver,
    NotEnabledResolver,

    { provide: HTTP_INTERCEPTORS, useClass: TestInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
