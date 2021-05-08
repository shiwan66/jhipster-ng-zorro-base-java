import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { ConsumoComponent } from './consumo.component';
import { ConsumoDetailComponent } from './consumo-detail.component';
import { ConsumoUpdateComponent } from './consumo-update.component';
import { ConsumoDeleteDialogComponent } from './consumo-delete-dialog.component';
import { consumoRoute } from './consumo.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(consumoRoute)],
  declarations: [ConsumoComponent, ConsumoDetailComponent, ConsumoUpdateComponent, ConsumoDeleteDialogComponent],
  entryComponents: [ConsumoDeleteDialogComponent]
})
export class NgzorroConsumoModule {}
