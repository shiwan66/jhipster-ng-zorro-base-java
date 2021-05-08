import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAtividade } from 'app/shared/model/atividade.model';
import { AtividadeService } from './atividade.service';

@Component({
  templateUrl: './atividade-delete-dialog.component.html'
})
export class AtividadeDeleteDialogComponent {
  atividade: IAtividade;

  constructor(protected atividadeService: AtividadeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.atividadeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'atividadeListModification',
        content: 'Deleted an atividade'
      });
      this.activeModal.dismiss(true);
    });
  }
}
