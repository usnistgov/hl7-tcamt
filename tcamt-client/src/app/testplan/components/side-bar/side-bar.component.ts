import { ChangeDetectionStrategy, Component, computed, Input, model, ModelSignal, signal, Signal, WritableSignal } from '@angular/core';
import { combineLatest, map, take, tap } from 'rxjs';
import { Store } from '@ngrx/store';
import { CommonModule } from '@angular/common';
import { FaIconComponent } from '@fortawesome/angular-fontawesome';
import { RouterModule } from '@angular/router';
import { DamSideBarToggleComponent } from '@usnistgov/ngx-dam-framework';
import { FormsModule } from '@angular/forms';
import { ISectionLink, ISectionLinkDisplay } from '../../models/testplan';
import { SectionLinkDisplayState } from '../test-plan-widget/test-plan-widget.component';
@Component({
  selector: 'app-side-bar',
  standalone: true,
  imports: [
    CommonModule,
    FaIconComponent,
    RouterModule,
    DamSideBarToggleComponent,
    FormsModule,
  ],
  templateUrl: './side-bar.html',
  styleUrl: './side-bar.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SideBarComponent {
  @Input({ required: true })
  set links(links: ISectionLink[]) {
    combineLatest(
      links.map((link) => SectionLinkDisplayState.findById(this.store, link.id))
    ).pipe(
      take(1),
      map((sectionLinkList) => sectionLinkList.filter((v) => !!v)),
      tap((sectionLinkList) => { this.sections.set(sectionLinkList) })
    ).subscribe();
  }

  filterText: ModelSignal<string> = model('');
  sections: WritableSignal<ISectionLinkDisplay[]> = signal([]);
  filteredSections!: Signal<ISectionLinkDisplay[]>;

  constructor(private store: Store) {
    this.filteredSections = computed(() => {
      const text = this.filterText();
      const sections = this.sections();
      if (text) {
        return sections.filter((section) => section.label && section.label.toLowerCase().includes(text.toLowerCase()));
      } else {
        return sections;
      }
    });
  }

}
