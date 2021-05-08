import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { AnimalCarrapaticidaComponent } from './animal-carrapaticida.component';
import { AnimalCarrapaticidaDetailComponent } from './animal-carrapaticida-detail.component';
import { AnimalCarrapaticidaUpdateComponent } from './animal-carrapaticida-update.component';
import { AnimalCarrapaticidaDeleteDialogComponent } from './animal-carrapaticida-delete-dialog.component';
import { animalCarrapaticidaRoute } from './animal-carrapaticida.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(animalCarrapaticidaRoute)],
  declarations: [
    AnimalCarrapaticidaComponent,
    AnimalCarrapaticidaDetailComponent,
    AnimalCarrapaticidaUpdateComponent,
    AnimalCarrapaticidaDeleteDialogComponent
  ],
  entryComponents: [AnimalCarrapaticidaDeleteDialogComponent]
})
export class NgzorroAnimalCarrapaticidaModule {}
