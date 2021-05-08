import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { AnimalAlteracaoComponent } from './animal-alteracao.component';
import { AnimalAlteracaoDetailComponent } from './animal-alteracao-detail.component';
import { AnimalAlteracaoUpdateComponent } from './animal-alteracao-update.component';
import { AnimalAlteracaoDeleteDialogComponent } from './animal-alteracao-delete-dialog.component';
import { animalAlteracaoRoute } from './animal-alteracao.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(animalAlteracaoRoute)],
  declarations: [
    AnimalAlteracaoComponent,
    AnimalAlteracaoDetailComponent,
    AnimalAlteracaoUpdateComponent,
    AnimalAlteracaoDeleteDialogComponent
  ],
  entryComponents: [AnimalAlteracaoDeleteDialogComponent]
})
export class NgzorroAnimalAlteracaoModule {}
