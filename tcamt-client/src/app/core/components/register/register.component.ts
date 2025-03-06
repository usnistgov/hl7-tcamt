import { Component } from '@angular/core';
import { AlertService, DamAlertsContainerComponent, IMessage, MessageType } from '@usnistgov/ngx-dam-framework';
import { Store } from '@ngrx/store';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { catchError, map, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    DamAlertsContainerComponent,
    ReactiveFormsModule,
    FormsModule,
    CommonModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  formGroup: FormGroup;

  constructor(
    private store: Store,
    private alerts: AlertService,
    private http: HttpClient,
  ) {
    this.formGroup = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(15), Validators.pattern('^[a-zA-Z-_0-9]+$')]),
      password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(30), Validators.pattern('^[a-zA-Z-_0-9]+$')]),
      email: new FormControl('', [Validators.required, Validators.minLength(5)]),
      confirmPassword: new FormControl('', [Validators.required]),
    })
  }

  submit() {
    if (this.formGroup.valid) {
      const value = this.formGroup.getRawValue();
      this.http.post<IMessage<any>>('/api/register', value).pipe(
        map((message) => {
          this.store.dispatch(
            this.alerts.getAlertAddAction(message, {
              tags: ['REGISTER_ISSUES']
            })
          );
        }),
        catchError((error) => {
          this.store.dispatch(
            this.alerts.getAlertAddActionFromHttpErrorResponse(error, null, {
              tags: ['REGISTER_ISSUES']
            })
          );
          return throwError(() => error);
        })
      ).subscribe();
    }
  }
}
