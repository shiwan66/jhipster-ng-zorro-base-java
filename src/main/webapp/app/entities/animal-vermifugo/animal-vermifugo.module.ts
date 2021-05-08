import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { AnimalVermifugoComponent } from './animal-vermifugo.component';
import { AnimalVermifugoDetailComponent } from './animal-vermifugo-detail.component';
import { AnimalVermifugoUpdateComponent } from './animal-vermifugo-update.component';
import { AnimalVermifugoDeleteDialogComponent } from './animal-vermifugo-delete-dialog.component';
import { animalVermifugoRoute } from './animal-vermifugo.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(animalVermifugoRoute)],
  declarations: [
    AnimalVermifugoComponent,
    AnimalVermifugoDetailComponent,
    AnimalVermifugoUpdateComponent,
    AnimalVermifugoDeleteDialogComponent
  ],
  entryComponents: [AnimalVermifugoDeleteDialogComponent]
})
export class NgzorroAnimalVermifugoModule {}
