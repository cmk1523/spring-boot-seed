import {Route} from '@angular/router';
import {HomeComponent} from './core/home/home.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {BaseResolver} from './shared/resolvers/BaseResolver';
import {UsersComponent} from './core/users/users/users.component';
import {UsersResolver} from './shared/resolvers/UsersResolver';
import {UserInfoComponent} from './core/user-info/user-info.component';
import {AppInfoComponent} from './core/app-info/app-info.component';
import {UserComponent} from './core/users/user/user.component';
import {UserResolver} from './shared/resolvers/UserResolver';
import {CreateUserComponent} from './core/users/create-user/create-user.component';
import {ErrorComponent} from './error/error.component';

// **
// REMINDER: Order is important!
// **
export const routes: Route[] = [
  {
    path: '',
    component: HomeComponent,
    resolve: {
      data: BaseResolver
    }
  },
  {
    path: 'error',
    component: ErrorComponent,
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
    path: 'appInfo',
    component: AppInfoComponent,
    resolve: {
      data: BaseResolver
    }
  },
  {
    path: 'userInfo',
    component: UserInfoComponent,
    resolve: {
      data: BaseResolver
    }
  },
  {
    path: 'users',
    component: UsersComponent,
    resolve: {
      data: UsersResolver
    }
  },
  {
    path: 'users/create',
    component: CreateUserComponent,
    resolve: {
      data: BaseResolver
    }
  },
  {
    path: 'users/:id',
    component: UserComponent,
    resolve: {
      data: UserResolver
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
