import { NgModule } from '@angular/core';
import { NgzorroSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { JhiAlertComponent } from './alert/alert.component';
import { JhiAlertErrorComponent } from './alert/alert-error.component';
import { JhiLoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
// eslint-disable-next-line @typescript-eslint/camelcase
import { NZ_I18N, pt_BR } from 'ng-zorro-antd';
import { NzIconDefinitionModule } from './util/ngIconDefinitionModule';

@NgModule({
  imports: [NgzorroSharedLibsModule, NzIconDefinitionModule],
  declarations: [FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent, JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [
    NzIconDefinitionModule,
    NgzorroSharedLibsModule,
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent,
    JhiLoginModalComponent,
    HasAnyAuthorityDirective
  ],
  providers: [
    // eslint-disable-next-line @typescript-eslint/camelcase
    { provide: NZ_I18N, useValue: pt_BR }
  ]
})
export class NgzorroSharedModule {}
