import { LoginComponent } from './login.component';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LOGIN_ROUTE } from './login.route';
import { NgzorroSharedLibsModule } from 'app/shared/shared-libs.module';

@NgModule({
  imports: [NgzorroSharedLibsModule, RouterModule.forChild([LOGIN_ROUTE])],
  declarations: [LoginComponent]
})
export class NgzorroLoginModule {}
