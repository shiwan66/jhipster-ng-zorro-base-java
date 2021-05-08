import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { VendaConsumoComponent } from './venda-consumo.component';
import { VendaConsumoDetailComponent } from './venda-consumo-detail.component';
import { VendaConsumoUpdateComponent } from './venda-consumo-update.component';
import { VendaConsumoDeleteDialogComponent } from './venda-consumo-delete-dialog.component';
import { vendaConsumoRoute } from './venda-consumo.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(vendaConsumoRoute)],
  declarations: [VendaConsumoComponent, VendaConsumoDetailComponent, VendaConsumoUpdateComponent, VendaConsumoDeleteDialogComponent],
  entryComponents: [VendaConsumoDeleteDialogComponent]
})
export class NgzorroVendaConsumoModule {}
