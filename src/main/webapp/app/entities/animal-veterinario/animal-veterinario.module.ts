import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { AnimalVeterinarioComponent } from './animal-veterinario.component';
import { AnimalVeterinarioDetailComponent } from './animal-veterinario-detail.component';
import { AnimalVeterinarioUpdateComponent } from './animal-veterinario-update.component';
import { AnimalVeterinarioDeleteDialogComponent } from './animal-veterinario-delete-dialog.component';
import { animalVeterinarioRoute } from './animal-veterinario.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(animalVeterinarioRoute)],
  declarations: [
    AnimalVeterinarioComponent,
    AnimalVeterinarioDetailComponent,
    AnimalVeterinarioUpdateComponent,
    AnimalVeterinarioDeleteDialogComponent
  ],
  entryComponents: [AnimalVeterinarioDeleteDialogComponent]
})
export class NgzorroAnimalVeterinarioModule {}
