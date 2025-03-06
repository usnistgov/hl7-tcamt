import { CommonModule } from '@angular/common';
import { Component, forwardRef } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DamLayoutComponent, DamSideBarToggleComponent, DamFullscreenButtonComponent, DamSaveButtonComponent, DamResetButtonComponent, DamAlertsContainerComponent, DamWidgetComponent, DataStateValue, DataStateRepository } from '@usnistgov/ngx-dam-framework';
import { Observable } from 'rxjs';
import { FaIconComponent } from '@fortawesome/angular-fontawesome';
import { TestPlanService } from '../../services/testplan.service';
import { ISectionLinkDisplay, ITestPlan } from '../../models/testplan';


export const TestPlanState = new DataStateValue<ITestPlan>({
  key: 'testplan',
  routeLoader: (params, injector) => {
    const dataService = injector.get(TestPlanService);
    return dataService.getTestPlanState(params['testplanId']);
  }
});

export const SectionLinkDisplayState = new DataStateRepository<ISectionLinkDisplay>({
  name: 'sectionLinkDisplay',
  routeLoader: (params, injector) => {
    const dataService = injector.get(TestPlanService);
    return dataService.getSectionLinkDisplayForEntity(params['testplanId']);
  }
});

export const TEST_PLAN_WIDGET_ID = 'TEST_PLAN_WIDGET_ID';

@Component({
  selector: 'app-test-plan-widget',
  standalone: true,
  imports: [CommonModule,
    DamLayoutComponent,
    DamSideBarToggleComponent,
    DamFullscreenButtonComponent,
    DamSaveButtonComponent,
    DamResetButtonComponent,
    DamAlertsContainerComponent,
    RouterModule,
    FaIconComponent],
  providers: [
    {
      provide: DamWidgetComponent,
      useExisting: forwardRef(() => TestPlanWidgetComponent)
    },
  ],
  templateUrl: './test-plan-widget.component.html',
  styleUrl: './test-plan-widget.component.scss',
})

export class TestPlanWidgetComponent extends DamWidgetComponent {
  testPlan$: Observable<ITestPlan>;

  constructor() {
    super(TEST_PLAN_WIDGET_ID);
    this.testPlan$ = TestPlanState.getValue(this.store);
  }



}
