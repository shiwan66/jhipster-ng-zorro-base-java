import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnimalTipoDeAlteracao } from 'app/shared/model/animal-tipo-de-alteracao.model';
import { AnimalTipoDeAlteracaoService } from './animal-tipo-de-alteracao.service';

@Component({
  templateUrl: './animal-tipo-de-alteracao-delete-dialog.component.html'
})
export class AnimalTipoDeAlteracaoDeleteDialogComponent {
  animalTipoDeAlteracao: IAnimalTipoDeAlteracao;

  constructor(
    protected animalTipoDeAlteracaoService: AnimalTipoDeAlteracaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.animalTipoDeAlteracaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'animalTipoDeAlteracaoListModification',
        content: 'Deleted an animalTipoDeAlteracao'
      });
      this.activeModal.dismiss(true);
    });
  }
}
