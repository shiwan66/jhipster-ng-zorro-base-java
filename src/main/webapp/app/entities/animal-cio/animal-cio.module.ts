import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { AnimalCioComponent } from './animal-cio.component';
import { AnimalCioDetailComponent } from './animal-cio-detail.component';
import { AnimalCioUpdateComponent } from './animal-cio-update.component';
import { AnimalCioDeleteDialogComponent } from './animal-cio-delete-dialog.component';
import { animalCioRoute } from './animal-cio.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(animalCioRoute)],
  declarations: [AnimalCioComponent, AnimalCioDetailComponent, AnimalCioUpdateComponent, AnimalCioDeleteDialogComponent],
  entryComponents: [AnimalCioDeleteDialogComponent]
})
export class NgzorroAnimalCioModule {}
