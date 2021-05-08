import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnimalAlteracao } from 'app/shared/model/animal-alteracao.model';
import { AnimalAlteracaoService } from './animal-alteracao.service';

@Component({
  templateUrl: './animal-alteracao-delete-dialog.component.html'
})
export class AnimalAlteracaoDeleteDialogComponent {
  animalAlteracao: IAnimalAlteracao;

  constructor(
    protected animalAlteracaoService: AnimalAlteracaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.animalAlteracaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'animalAlteracaoListModification',
        content: 'Deleted an animalAlteracao'
      });
      this.activeModal.dismiss(true);
    });
  }
}
