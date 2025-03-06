import { Injector } from "@angular/core";
import { IHeaderMenuOptions } from "./core/components/header/header.component";
import { Store } from "@ngrx/store";
import { LogoutRequestAction } from "@usnistgov/ngx-dam-framework";

// Hint (Header): Update this to configure you header menu options
export const HEADERS: IHeaderMenuOptions = Object.freeze({
  activeMainMenuClass: 'active',
  activeSubMenuClass: 'active-dropdown',
  menu: [
    {
      label: 'Home',
      routerLink: '/home',
      faIcon: 'user',
    },
    {
      label: 'Entity',
      routerLink: '/entity',
      children: [
        {
          label: 'Create Entity',
          routerLink: '/entity/create',
          faIcon: 'add',
        },
        {
          label: 'Entity List',
          routerLink: '/entity/list',
          queryParams: { type: "all" }
        }
      ]
    },
    {
      label: 'Test Plans',
      routerLink: '/testplan',
      children: [
        {
          label: 'Create Test Plan',
          routerLink: '/testplan/create',
          faIcon: 'add',
        },
        {
          label: 'Test Plan List',
          routerLink: '/testplan/list',
          queryParams: { type: "all" }
        }
      ]
    }

  ],
  accountMenu: {
    loggedOut: [
      {
        label: 'Login',
        routerLink: '/login'
      },
      {
        label: 'Register',
        routerLink: '/register'
      }
    ],
    loggedIn: [
      {
        label: 'Logout',
        handler: (injector: Injector) => {
          const store = injector.get(Store);
          store.dispatch(new LogoutRequestAction());
        }
      }
    ]
  }
})
