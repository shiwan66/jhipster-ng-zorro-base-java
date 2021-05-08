import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnexoAtendimento } from 'app/shared/model/anexo-atendimento.model';
import { AnexoAtendimentoService } from './anexo-atendimento.service';

@Component({
  templateUrl: './anexo-atendimento-delete-dialog.component.html'
})
export class AnexoAtendimentoDeleteDialogComponent {
  anexoAtendimento: IAnexoAtendimento;

  constructor(
    protected anexoAtendimentoService: AnexoAtendimentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.anexoAtendimentoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'anexoAtendimentoListModification',
        content: 'Deleted an anexoAtendimento'
      });
      this.activeModal.dismiss(true);
    });
  }
}
