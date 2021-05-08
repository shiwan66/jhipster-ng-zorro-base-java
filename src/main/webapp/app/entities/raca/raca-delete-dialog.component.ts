import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRaca } from 'app/shared/model/raca.model';
import { RacaService } from './raca.service';

@Component({
  templateUrl: './raca-delete-dialog.component.html'
})
export class RacaDeleteDialogComponent {
  raca: IRaca;

  constructor(protected racaService: RacaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.racaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'racaListModification',
        content: 'Deleted an raca'
      });
      this.activeModal.dismiss(true);
    });
  }
}
