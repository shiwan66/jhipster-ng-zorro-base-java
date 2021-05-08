import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModeloAtividade } from 'app/shared/model/modelo-atividade.model';
import { ModeloAtividadeService } from './modelo-atividade.service';

@Component({
  templateUrl: './modelo-atividade-delete-dialog.component.html'
})
export class ModeloAtividadeDeleteDialogComponent {
  modeloAtividade: IModeloAtividade;

  constructor(
    protected modeloAtividadeService: ModeloAtividadeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.modeloAtividadeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'modeloAtividadeListModification',
        content: 'Deleted an modeloAtividade'
      });
      this.activeModal.dismiss(true);
    });
  }
}
