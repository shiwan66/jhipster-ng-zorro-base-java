import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Jhipster51SysCompanyModule } from './sys-company/sys-company.module';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'anexo',
        loadChildren: () => import('./anexo/anexo.module').then(m => m.NgzorroAnexoModule)
      },
      {
        path: 'sys-company',
        loadChildren: () => import('./sys-company/sys-company.module').then(m => m.Jhipster51SysCompanyModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class NgzorroEntityModule {}
