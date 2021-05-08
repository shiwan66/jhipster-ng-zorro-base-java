import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVendaConsumo } from 'app/shared/model/venda-consumo.model';
import { VendaConsumoService } from './venda-consumo.service';

@Component({
  templateUrl: './venda-consumo-delete-dialog.component.html'
})
export class VendaConsumoDeleteDialogComponent {
  vendaConsumo: IVendaConsumo;

  constructor(
    protected vendaConsumoService: VendaConsumoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vendaConsumoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'vendaConsumoListModification',
        content: 'Deleted an vendaConsumo'
      });
      this.activeModal.dismiss(true);
    });
  }
}
