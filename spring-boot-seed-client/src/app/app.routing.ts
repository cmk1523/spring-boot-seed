import {Route} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {BaseResolver} from './shared/resolvers/BaseResolver';

export const routes: Route[] = [
  {
    path: '',
    component: HomeComponent,
    resolve: {
      data: BaseResolver
    }
  },
  {
    path: 'home',
    component: HomeComponent,
    resolve: {
      data: BaseResolver
    }
  },
  {
    path: '**',
    component: PageNotFoundComponent,
    resolve: {
      data: BaseResolver
    }
  }
];
