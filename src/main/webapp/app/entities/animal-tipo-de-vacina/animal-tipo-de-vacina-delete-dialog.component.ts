import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnimalTipoDeVacina } from 'app/shared/model/animal-tipo-de-vacina.model';
import { AnimalTipoDeVacinaService } from './animal-tipo-de-vacina.service';

@Component({
  templateUrl: './animal-tipo-de-vacina-delete-dialog.component.html'
})
export class AnimalTipoDeVacinaDeleteDialogComponent {
  animalTipoDeVacina: IAnimalTipoDeVacina;

  constructor(
    protected animalTipoDeVacinaService: AnimalTipoDeVacinaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.animalTipoDeVacinaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'animalTipoDeVacinaListModification',
        content: 'Deleted an animalTipoDeVacina'
      });
      this.activeModal.dismiss(true);
    });
  }
}
