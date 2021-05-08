import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { MovimentacaoDeEstoqueComponent } from './movimentacao-de-estoque.component';
import { MovimentacaoDeEstoqueDetailComponent } from './movimentacao-de-estoque-detail.component';
import { MovimentacaoDeEstoqueUpdateComponent } from './movimentacao-de-estoque-update.component';
import { MovimentacaoDeEstoqueDeleteDialogComponent } from './movimentacao-de-estoque-delete-dialog.component';
import { movimentacaoDeEstoqueRoute } from './movimentacao-de-estoque.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(movimentacaoDeEstoqueRoute)],
  declarations: [
    MovimentacaoDeEstoqueComponent,
    MovimentacaoDeEstoqueDetailComponent,
    MovimentacaoDeEstoqueUpdateComponent,
    MovimentacaoDeEstoqueDeleteDialogComponent
  ],
  entryComponents: [MovimentacaoDeEstoqueDeleteDialogComponent]
})
export class NgzorroMovimentacaoDeEstoqueModule {}
