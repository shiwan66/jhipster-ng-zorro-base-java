import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnexo } from 'app/shared/model/anexo.model';
import { AnexoService } from './anexo.service';

@Component({
  templateUrl: './anexo-delete-dialog.component.html'
})
export class AnexoDeleteDialogComponent {
  anexo: IAnexo;

  constructor(protected anexoService: AnexoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.anexoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'anexoListModification',
        content: 'Deleted an anexo'
      });
      this.activeModal.dismiss(true);
    });
  }
}
