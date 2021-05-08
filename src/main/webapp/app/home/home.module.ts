import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
  // eslint-disable-next-line @typescript-eslint/camelcase
})
export class NgzorroHomeModule {}
