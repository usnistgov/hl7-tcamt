import { Component, inject } from '@angular/core';
import { AlertService, DataStateRepository, IListItemControl, ListWidgetComponent, MessageType, SortOrder, UtilityService } from '@usnistgov/ngx-dam-framework';
import { Store } from '@ngrx/store';
import { map, of, skip, switchMap, tap, throwError } from 'rxjs'; import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, RouterLink, RouterLinkActive } from '@angular/router';
import { IEntityDescriptor } from '../../models/testplan';
import { TestPlanService } from '../../services/testplan.service';
import { MatDialog } from '@angular/material/dialog';

export const TestPlanListState = new DataStateRepository<IEntityDescriptor>({
  name: 'entityList',
  routeLoader: (params, injector, state) => {
    const testPlan = injector.get(TestPlanService);
    return testPlan.getList(state.route.queryParams['type']);
  }
});

@Component({
  selector: 'app-test-plan-list',
  standalone: true,
  imports: [
    CommonModule,
    ListWidgetComponent,
    RouterLink,
    RouterLinkActive,
    FormsModule
  ],
  templateUrl: './test-plan-list.component.html',
  styleUrl: './test-plan-list.component.scss'
})
export class TestPlanListComponent {

  testPlanList = TestPlanListState;
  route = inject(ActivatedRoute);
  router = inject(Router);
  sortOrder = SortOrder.ASC;

  controls: IListItemControl<IEntityDescriptor>[] = [{
    key: 'open',
    label: 'Open',
    btnClass: 'btn btn-sm btn-primary',
    iconClass: "bi bi-enter",
    disabled: () => false,
    hidden: () => false,
    onClick: (item: IEntityDescriptor) => {
      console.log(item);
      this.router.navigate(["/", "testplans", item.id]);
    }
  }]
  sort: (field: string | undefined, order: SortOrder) => (a: IEntityDescriptor, b: IEntityDescriptor) => number = (sortField, sortOrder) => (a, b) => {
    const multiplier = sortOrder === SortOrder.ASC ? 1 : -1;
    switch (sortField) {
      case 'name':
        return (a.label.toLowerCase() < b.label.toLowerCase() ? -1 : 1) * multiplier;
    }
    return 0;
  }
  sortOptions = [{
    name: 'Name',
    key: 'name',
  }];

  sortOrderToggle = SortOrder.ASC;
  linkTabs = [
    {
      label: 'Owned',
      routerLink: {
        command: [],
        params: {
          type: 'owned'
        }
      }
    },
    {
      label: 'Public',
      routerLink: {
        command: [],
        params: {
          type: 'public'
        }
      }
    },
    {
      label: 'All',
      routerLink: {
        command: [],
        params: {
          type: 'all'
        }
      }
    }
  ];

  constructor(
    private store: Store,
    private routeSnapshot: ActivatedRoute,
    private testPlanService: TestPlanService,
    private dialog: MatDialog,
    private utilityService: UtilityService) {

  }

  ngOnInit(): void {
    this.utilityService.clearAlerts();
    this.routeSnapshot.queryParams.pipe(
      switchMap((queryParams) => {
        return this.testPlanService.getList(queryParams['type']).pipe(
          tap((values) => {
            TestPlanListState.setValue(this.store, values);
          })
        );
      })
    ).subscribe();
  }



}
