import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { AnimalVacinaComponent } from './animal-vacina.component';
import { AnimalVacinaDetailComponent } from './animal-vacina-detail.component';
import { AnimalVacinaUpdateComponent } from './animal-vacina-update.component';
import { AnimalVacinaDeleteDialogComponent } from './animal-vacina-delete-dialog.component';
import { animalVacinaRoute } from './animal-vacina.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(animalVacinaRoute)],
  declarations: [AnimalVacinaComponent, AnimalVacinaDetailComponent, AnimalVacinaUpdateComponent, AnimalVacinaDeleteDialogComponent],
  entryComponents: [AnimalVacinaDeleteDialogComponent]
})
export class NgzorroAnimalVacinaModule {}
