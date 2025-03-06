import { TestPlanService } from './../../services/testplan.service';
import { Component } from '@angular/core';
import { AlertService, DamAlertsContainerComponent, MessageType, UtilityService } from '@usnistgov/ngx-dam-framework';
import { Store } from '@ngrx/store';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { mergeAll, tap } from 'rxjs';

@Component({
  selector: 'app-create-test-plan',
  standalone: true,
  imports: [
    DamAlertsContainerComponent
  ],
  templateUrl: './create-test-plan.component.html',
  styleUrl: './create-test-plan.component.scss'
})
export class CreateTestPlanComponent {

  form: FormGroup;

  constructor(
    private store: Store,
    private alerts: AlertService,
    private _formBuilder: FormBuilder,
    private utilityService: UtilityService,
    private router: Router,
    private testPlanService: TestPlanService

  ) {
    this.form = this._formBuilder.group({
      name: ['', Validators.required],
      description: ['']
    })
  }



  submit() {
    console.log(this.form.getRawValue());
    this.utilityService.useLoaderWithErrorAlert(
      this.testPlanService.createTestPlan(this.form.getRawValue()),
      {
        message: {
          fromHttpResponse: true,
          tags: ['CREATE_TEST_PLAN_ISSUES']
        },
        loader: {
          blockUI: true
        }
      }
    ).pipe(
      tap((message) => {
        console.log(message);
        this.router.navigate(["/testplans", message.id]);

      }),
    ).subscribe();
  }
}
