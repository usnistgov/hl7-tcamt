import { TEST_PLAN_WIDGET_ID } from './../components/test-plan-widget/test-plan-widget.component';
import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { IEntity, ISection, ISectionLinkDisplay, ITestPlan, ITestPlanDescriptor, SectionType } from '../models/testplan';
import { IEntityDescriptor } from '../../entity/models/entity';
import { MessageType } from '@usnistgov/ngx-dam-framework';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TestPlanService {

  readonly TEST_PLAN_END_POINT = '/api/testplans/';

  constructor(private http: HttpClient) { }

  getList(type: string): Observable<IEntityDescriptor[]> {
    console.log('getList', type);
    return this.http.get<ITestPlanDescriptor[]>(`${this.TEST_PLAN_END_POINT}list?type=${type}`);
  }

  createTestPlan(testPan: ITestPlanCreate): Observable<ITestPlan> {
    return this.http.post<ITestPlan>(`${this.TEST_PLAN_END_POINT}` + 'create', testPan);
  }

  getEntityValue(id: string): Observable<IEntity> {
    return of({
      id,
      label: 'Entity ' + id,
      sections: [
        {
          id: '1',
          type: SectionType.TEXT,
        },
        {
          id: '2',
          type: SectionType.FORM,
        },
      ]
    })
  }

  getSectionLinkDisplayForEntity(id: string): Observable<ISectionLinkDisplay[]> {
    return of([
      {
        id: '1',
        type: SectionType.TEXT,
        label: 'Some Text Section'
      },
      {
        id: '2',
        type: SectionType.FORM,
        label: 'Some Form Section'
      },
    ])
  }

  getSection(id: string): Observable<ISection> {
    if (id === '1') {
      return of({
        id: '1',
        type: SectionType.TEXT,
        label: 'Some Text Section',
        value: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
      })
    } else if (id === '2') {
      return of({
        id: '2',
        type: SectionType.FORM,
        label: 'Some Form Section',
        fields: [{
          key: 'name',
          label: 'Name',
          value: 'This is a name'
        }, {
          key: 'desc',
          label: 'Description',
          value: 'This is some description'
        }]
      })
    } else {
      return throwError(() => ({
        status: MessageType.FAILED,
        text: 'Section ' + id + ' not found.'
      }));
    }
  }

  getTestPlanMetadata() {

  }

  getTestPlanState() {
    return
  }


}
export interface ITestPlanCreate {
  name: string;
  description: string;
}

