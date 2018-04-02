import {Route} from '@angular/router';
import {HomeComponent} from './core/home/home.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {BaseResolver} from './shared/resolvers/BaseResolver';
import {UsersComponent} from './core/users/users.component';

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
    path: 'users',
    component: UsersComponent,
    resolve: {
      data: BaseResolver
    }
  },
  {
    path: 'users/id',
    component: UsersComponent,
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
