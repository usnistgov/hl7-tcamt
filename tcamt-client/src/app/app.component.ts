import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { DamMainComponent, UserManagementHeaderComponent } from '@usnistgov/ngx-dam-framework';
import { HeaderComponent } from './core/components/header/header.component';
import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faChevronRight, faFile, faFilter, faList, faMinus, faPencil, faPlus, faUser } from '@fortawesome/free-solid-svg-icons';
import { HEADERS } from './headers.configuration';

import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    HeaderComponent,
    CommonModule,
    DamMainComponent,
    UserManagementHeaderComponent,
    RouterOutlet,
    RouterModule,
    FontAwesomeModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = "Test Case Authoring And Management Tool";
  abbrv = "TCAMT";
  version = "v1.0.0";
  menuOptions = HEADERS;



  // Hint (Icons): add icons from font-awesome here, to reference in the rest of the app
  constructor(iconLibrary: FaIconLibrary, private http: HttpClient) {
    iconLibrary.addIcons(faUser);
    iconLibrary.addIcons(faFile);
    iconLibrary.addIcons(faPencil);
    iconLibrary.addIcons(faList);
    iconLibrary.addIcons(faFilter);
    iconLibrary.addIcons(faPlus);
    iconLibrary.addIcons(faMinus);
    iconLibrary.addIcons(faChevronRight);

  }

  testAPI() {
    this.http.get('/api/test/public').subscribe(x => console.log(x));
  }
}
