import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-test-case-metadata',
  standalone: true,
  imports: [],
  templateUrl: './test-case-metadata.component.html',
  styleUrl: './test-case-metadata.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TestCaseMetadataComponent { }
