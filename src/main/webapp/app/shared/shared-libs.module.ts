import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgJhipsterModule } from 'ng-jhipster';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
// eslint-disable-next-line @typescript-eslint/camelcase
import { NgZorroAntdModule } from 'ng-zorro-antd';

@NgModule({
  exports: [
    FormsModule,
    CommonModule,
    NgJhipsterModule,
    InfiniteScrollModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    TranslateModule,
    NgZorroAntdModule
  ]
  // eslint-disable-next-line @typescript-eslint/camelcase
})
export class NgzorroSharedLibsModule {}
