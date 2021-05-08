import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { AnimalTipoDeAlteracaoComponent } from './animal-tipo-de-alteracao.component';
import { AnimalTipoDeAlteracaoDetailComponent } from './animal-tipo-de-alteracao-detail.component';
import { AnimalTipoDeAlteracaoUpdateComponent } from './animal-tipo-de-alteracao-update.component';
import { AnimalTipoDeAlteracaoDeleteDialogComponent } from './animal-tipo-de-alteracao-delete-dialog.component';
import { animalTipoDeAlteracaoRoute } from './animal-tipo-de-alteracao.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(animalTipoDeAlteracaoRoute)],
  declarations: [
    AnimalTipoDeAlteracaoComponent,
    AnimalTipoDeAlteracaoDetailComponent,
    AnimalTipoDeAlteracaoUpdateComponent,
    AnimalTipoDeAlteracaoDeleteDialogComponent
  ],
  entryComponents: [AnimalTipoDeAlteracaoDeleteDialogComponent]
})
export class NgzorroAnimalTipoDeAlteracaoModule {}
