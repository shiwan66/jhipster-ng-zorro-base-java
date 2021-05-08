import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnimalObservacao } from 'app/shared/model/animal-observacao.model';
import { AnimalObservacaoService } from './animal-observacao.service';

@Component({
  templateUrl: './animal-observacao-delete-dialog.component.html'
})
export class AnimalObservacaoDeleteDialogComponent {
  animalObservacao: IAnimalObservacao;

  constructor(
    protected animalObservacaoService: AnimalObservacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.animalObservacaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'animalObservacaoListModification',
        content: 'Deleted an animalObservacao'
      });
      this.activeModal.dismiss(true);
    });
  }
}
