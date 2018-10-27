import {Route} from '@angular/router';
import {PageNotFoundComponent} from './invalid/page-not-found/page-not-found.component';
import {BaseResolver} from './shared/resolvers/BaseResolver';
import {UserInfoComponent} from './core/user-info/user-info.component';
import {AppInfoComponent} from './core/app-info/app-info.component';
import {HomeComponent} from './home/home.component';
import {ErrorComponent} from './invalid/error/error.component';
import {UnauthorizedComponent} from './invalid/unauthorized/unauthorized.component';
import {HomeResolver} from './shared/resolvers/HomeResolver';
import {NotEnabledComponent} from './invalid/not-enabled/not-enabled.component';
import {NotEnabledResolver} from './shared/resolvers/NotEnabledResolver';

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
    path: 'unauthorized',
    component: UnauthorizedComponent,
    resolve: {
      data: BaseResolver
    }
  },
  {
    path: 'notenabled',
    component: NotEnabledComponent,
    resolve: {
      data: NotEnabledResolver
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
    path: '**',
    component: PageNotFoundComponent,
    resolve: {
      data: BaseResolver
    }
  }
];
