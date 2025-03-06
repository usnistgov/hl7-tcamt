import { HttpErrorResponse, HttpRequest } from "@angular/common/http";
import { Injector } from "@angular/core";
import { Router } from "@angular/router";
import { IAuthenticationConfig, MessageType } from "@usnistgov/ngx-dam-framework";
import { of, throwError } from "rxjs";

// Hint (Authentication) : Update these values to match your backend authentication
export const AUTHENTICATION_CONFIGURATION: IAuthenticationConfig = Object.freeze({
  api: {
    login: '/api/login',
    checkAuthStatus: '/api/me',
    logout: '/api/logout',
    checkLinkToken: '/api/password/validatetoken',
  },
  providers: [
    {
      url: '/oauth2/authorization/nist-okta',
      label: 'Department Of Commerce Login'
    }
  ],
  userPreRequirement: {
    handle: (request: HttpRequest<any>, response: HttpErrorResponse, inject: Injector) => {
      if (response.error && response.error.data === 'setup-profile' && response.status === 403) {
        const router = inject.get(Router);
        router.navigate(["/setup-profile"], { queryParams: { return: request.url } })
        return of();
      }
      return throwError(() => response);
    }
  },
  forgotPasswordUrl: '/forgot-password',
  loginPageRedirectUrl: '/login',
  unprotectedRedirectUrl: '/home',
  loginSuccessRedirectUrl: '/home',
  sessionTimeoutStatusCodes: [401],
  unauthorized: {
    status: MessageType.FAILED,
    text: 'You do not have permission to access this page',
  },
})
