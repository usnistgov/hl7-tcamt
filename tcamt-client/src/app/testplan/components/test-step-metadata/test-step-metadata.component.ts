import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-test-step-metadata',
  standalone: true,
  imports: [],
  templateUrl: './test-step-metadata.component.html',
  styleUrl: './test-step-metadata.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TestStepMetadataComponent { }

