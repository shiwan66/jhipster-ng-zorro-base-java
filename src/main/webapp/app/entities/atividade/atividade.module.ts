import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { AtividadeComponent } from './atividade.component';
import { AtividadeDetailComponent } from './atividade-detail.component';
import { AtividadeUpdateComponent } from './atividade-update.component';
import { AtividadeDeleteDialogComponent } from './atividade-delete-dialog.component';
import { atividadeRoute } from './atividade.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(atividadeRoute)],
  declarations: [AtividadeComponent, AtividadeDetailComponent, AtividadeUpdateComponent, AtividadeDeleteDialogComponent],
  entryComponents: [AtividadeDeleteDialogComponent]
})
export class NgzorroAtividadeModule {}
