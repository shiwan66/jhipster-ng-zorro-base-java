import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'anexo',
        loadChildren: () => import('./anexo/anexo.module').then(m => m.NgzorroAnexoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class NgzorroEntityModule {}
