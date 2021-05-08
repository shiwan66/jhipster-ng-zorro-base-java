import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { AnexoAtendimentoComponent } from './anexo-atendimento.component';
import { AnexoAtendimentoDetailComponent } from './anexo-atendimento-detail.component';
import { AnexoAtendimentoUpdateComponent } from './anexo-atendimento-update.component';
import { AnexoAtendimentoDeleteDialogComponent } from './anexo-atendimento-delete-dialog.component';
import { anexoAtendimentoRoute } from './anexo-atendimento.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(anexoAtendimentoRoute)],
  declarations: [
    AnexoAtendimentoComponent,
    AnexoAtendimentoDetailComponent,
    AnexoAtendimentoUpdateComponent,
    AnexoAtendimentoDeleteDialogComponent
  ],
  entryComponents: [AnexoAtendimentoDeleteDialogComponent]
})
export class NgzorroAnexoAtendimentoModule {}
