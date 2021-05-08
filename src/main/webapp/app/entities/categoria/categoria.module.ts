import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NgzorroSharedModule } from 'app/shared/shared.module';
import { CategoriaComponent } from './categoria.component';
import { CategoriaDetailComponent } from './categoria-detail.component';
import { CategoriaUpdateComponent } from './categoria-update.component';
import { CategoriaDeleteDialogComponent } from './categoria-delete-dialog.component';
import { categoriaRoute } from './categoria.route';

@NgModule({
  imports: [NgzorroSharedModule, RouterModule.forChild(categoriaRoute)],
  declarations: [CategoriaComponent, CategoriaDetailComponent, CategoriaUpdateComponent, CategoriaDeleteDialogComponent],
  entryComponents: [CategoriaDeleteDialogComponent]
})
export class NgzorroCategoriaModule {}
