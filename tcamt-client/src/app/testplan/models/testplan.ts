import { ITestPlanMetadata } from '../components/test-plan-metadata/test-plan-metadata.component';
import { ITestPlan } from './testplan';
import { IListEntity } from "@usnistgov/ngx-dam-framework";

export interface ITestPlan {
  id: string;
  label: string;
}

export interface IEntityDescriptor {
  id: string;
  label: string;
}

export interface IEntity {
  id: string;
  label: string;
  sections: ISectionLink[];
}

export enum SectionType {
  TEXT = 'TEXT',
  FORM = 'FORM'
}

export interface ISectionLink {
  id: string;
  type: SectionType;
}

export interface ISectionLinkDisplay extends ISectionLink {
  id: string;
  type: SectionType;
  label: string;
}

export interface ISection extends ISectionLink {
  label: string;
}

export interface ITextSection extends ISection {
  value: string;
}

export interface IFormSection extends ISection {
  fields: IField[];
}

export interface IField {
  key: string;
  label: string;
  value: string;
}
export interface ITestPlanState {
  metadata: ITestPlanMetadata;
}

export interface ITestPlanDescriptor extends IListEntity {
  id: string;
  name: string;
}
