import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConsumo } from 'app/shared/model/consumo.model';
import { ConsumoService } from './consumo.service';

@Component({
  templateUrl: './consumo-delete-dialog.component.html'
})
export class ConsumoDeleteDialogComponent {
  consumo: IConsumo;

  constructor(protected consumoService: ConsumoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.consumoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'consumoListModification',
        content: 'Deleted an consumo'
      });
      this.activeModal.dismiss(true);
    });
  }
}
