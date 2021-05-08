import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAtendimento } from 'app/shared/model/atendimento.model';
import { AtendimentoService } from './atendimento.service';

@Component({
  templateUrl: './atendimento-delete-dialog.component.html'
})
export class AtendimentoDeleteDialogComponent {
  atendimento: IAtendimento;

  constructor(
    protected atendimentoService: AtendimentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.atendimentoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'atendimentoListModification',
        content: 'Deleted an atendimento'
      });
      this.activeModal.dismiss(true);
    });
  }
}
