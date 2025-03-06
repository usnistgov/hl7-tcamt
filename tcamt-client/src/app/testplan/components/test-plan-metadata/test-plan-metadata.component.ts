import { TestPlanService } from './../../services/testplan.service';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DamAbstractEditorComponent, DamfEditorInitializer, ISaveResult, IStateCurrent, UtilityService } from '@usnistgov/ngx-dam-framework';
import { map, Observable } from 'rxjs';

@Component({
  selector: 'app-test-plan-metadata',
  standalone: true,
  imports: [],
  templateUrl: './test-plan-metadata.component.html',
  styleUrl: './test-plan-metadata.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export const EDITOR_ID = 'TEST_PLAN_METATDA_EDITOR';
export const TEST_PLAN_METADATA_EDITOR_INITIALIZER: DamfEditorInitializer<ITestPlanMetadata> = (params, injector) => {
  const testPlanService = injector.get(TestPlanService);
  return testPlanService.getTestPlanMetadata(params['testplanId']).pipe(
    map((value: ITestPlanMetadata) => {
      return {
        initial: value as ITestPlanMetadata,
        context: {
          elementId: value.id,
        }
      }
    })
  );
}


export class TestPlanMetadataComponent extends DamAbstractEditorComponent<ITestPlanMetadata> {




  constructor(private testPlanService: TestPlanService, private dialog: MatDialog,
    private utilityService: UtilityService) {
    super({
      id: EDITOR_ID,
      title: 'Metadata',
    });

  }


  override onEditorDataUpdate(data: IStateCurrent<ITestPlanMetadata, never>): void {
    throw new Error('Method not implemented.');
  }
  override AfterEditorNgViewInit(): void {
    throw new Error('Method not implemented.');
  }
  override onEditorNgInit(): void {
    throw new Error('Method not implemented.');
  }
  override onEditorNgDestoy(): void {
    throw new Error('Method not implemented.');
  }
  override save(current: IStateCurrent<ITestPlanMetadata, never>): Observable<ISaveResult<ITestPlanMetadata>> {
    throw new Error('Method not implemented.');
  }



}

export interface ITestPlanMetadata {
  id: string
}
