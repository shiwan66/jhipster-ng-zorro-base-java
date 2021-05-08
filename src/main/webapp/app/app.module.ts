import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { NgzorroSharedModule } from 'app/shared/shared.module';
import { NgzorroCoreModule } from 'app/core/core.module';
import { NgzorroAppRoutingModule } from './app-routing.module';
import { NgzorroEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// eslint-disable-next-line @typescript-eslint/camelcase
@NgModule({
  imports: [
    BrowserModule,
    NgzorroSharedModule,
    NgzorroCoreModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    NgzorroEntityModule,
    NgzorroAppRoutingModule,
    BrowserAnimationsModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class NgzorroAppModule {}
