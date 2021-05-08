import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { AnimalObservacaoComponent } from './animal-observacao.component';
import { AnimalObservacaoDetailComponent } from './animal-observacao-detail.component';
import { AnimalObservacaoUpdateComponent } from './animal-observacao-update.component';
import { AnimalObservacaoDeleteDialogComponent } from './animal-observacao-delete-dialog.component';
import { animalObservacaoRoute } from './animal-observacao.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(animalObservacaoRoute)],
  declarations: [
    AnimalObservacaoComponent,
    AnimalObservacaoDetailComponent,
    AnimalObservacaoUpdateComponent,
    AnimalObservacaoDeleteDialogComponent
  ],
  entryComponents: [AnimalObservacaoDeleteDialogComponent]
})
export class NgzorroAnimalObservacaoModule {}
