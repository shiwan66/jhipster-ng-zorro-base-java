import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { AnimalTipoDeVacinaComponent } from './animal-tipo-de-vacina.component';
import { AnimalTipoDeVacinaDetailComponent } from './animal-tipo-de-vacina-detail.component';
import { AnimalTipoDeVacinaUpdateComponent } from './animal-tipo-de-vacina-update.component';
import { AnimalTipoDeVacinaDeleteDialogComponent } from './animal-tipo-de-vacina-delete-dialog.component';
import { animalTipoDeVacinaRoute } from './animal-tipo-de-vacina.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(animalTipoDeVacinaRoute)],
  declarations: [
    AnimalTipoDeVacinaComponent,
    AnimalTipoDeVacinaDetailComponent,
    AnimalTipoDeVacinaUpdateComponent,
    AnimalTipoDeVacinaDeleteDialogComponent
  ],
  entryComponents: [AnimalTipoDeVacinaDeleteDialogComponent]
})
export class NgzorroAnimalTipoDeVacinaModule {}
