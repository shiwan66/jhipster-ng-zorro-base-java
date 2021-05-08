import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITitulo } from 'app/shared/model/titulo.model';
import { TituloService } from './titulo.service';

@Component({
  templateUrl: './titulo-delete-dialog.component.html'
})
export class TituloDeleteDialogComponent {
  titulo: ITitulo;

  constructor(protected tituloService: TituloService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tituloService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'tituloListModification',
        content: 'Deleted an titulo'
      });
      this.activeModal.dismiss(true);
    });
  }
}
