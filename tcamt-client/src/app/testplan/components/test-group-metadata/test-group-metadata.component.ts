import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-test-group-metadata',
  standalone: true,
  imports: [],
  templateUrl: './test-group-metadata.component.html',
  styleUrl: './test-group-metadata.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TestGroupMetadataComponent { }
