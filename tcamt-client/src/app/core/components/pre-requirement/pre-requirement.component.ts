import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { AlertService, DamAlertsContainerComponent, IMessage, UpdateAuthStatusAction } from '@usnistgov/ngx-dam-framework';
import { catchError, map, take, tap, throwError } from 'rxjs';

@Component({
  selector: 'app-pre-requirement',
  standalone: true,
  imports: [
    DamAlertsContainerComponent,
    ReactiveFormsModule,
    FormsModule,
    CommonModule
  ],
  templateUrl: './pre-requirement.component.html',
  styleUrl: './pre-requirement.component.scss'
})
export class PreRequirementComponent {

  formGroup: FormGroup;

  constructor(
    private store: Store,
    private alerts: AlertService,
    private http: HttpClient,
    private router: Router,
    private activeRoute: ActivatedRoute,
  ) {
    this.formGroup = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.minLength(7), Validators.maxLength(15), Validators.pattern('^[a-zA-Z-_0-9]+$')]),
    })
  }

  submit() {
    if (this.formGroup.valid) {
      const value = this.formGroup.getRawValue();
      this.http.post<IMessage<any>>('/api/setup-profile', value).pipe(
        map((message) => {
          this.store.dispatch(
            this.alerts.getAlertAddAction(message, {
              tags: ['PRE_REQUIREMENT_ISSUES']
            })
          );
          this.activeRoute.queryParamMap.pipe(
            take(1),
            tap((params) => {
              const returnTo = params.get('return');
              this.store.dispatch(new UpdateAuthStatusAction());
              this.router.navigate(returnTo ? [returnTo] : ["/"]);
            })
          ).subscribe();
        }),
        catchError((error) => {
          console.log("CAUGHT ERROR", error);
          this.store.dispatch(
            this.alerts.getAlertAddActionFromHttpErrorResponse(error, null, {
              tags: ['PRE_REQUIREMENT_ISSUES']
            })
          );
          return throwError(() => error);
        })
      ).subscribe();
    }
  }

}
