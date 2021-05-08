import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { ModeloAtividadeComponent } from './modelo-atividade.component';
import { ModeloAtividadeDetailComponent } from './modelo-atividade-detail.component';
import { ModeloAtividadeUpdateComponent } from './modelo-atividade-update.component';
import { ModeloAtividadeDeleteDialogComponent } from './modelo-atividade-delete-dialog.component';
import { modeloAtividadeRoute } from './modelo-atividade.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(modeloAtividadeRoute)],
  declarations: [
    ModeloAtividadeComponent,
    ModeloAtividadeDetailComponent,
    ModeloAtividadeUpdateComponent,
    ModeloAtividadeDeleteDialogComponent
  ],
  entryComponents: [ModeloAtividadeDeleteDialogComponent]
})
export class NgzorroModeloAtividadeModule {}
