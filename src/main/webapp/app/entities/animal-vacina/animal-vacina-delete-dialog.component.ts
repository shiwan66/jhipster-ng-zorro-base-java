import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnimalVacina } from 'app/shared/model/animal-vacina.model';
import { AnimalVacinaService } from './animal-vacina.service';

@Component({
  templateUrl: './animal-vacina-delete-dialog.component.html'
})
export class AnimalVacinaDeleteDialogComponent {
  animalVacina: IAnimalVacina;

  constructor(
    protected animalVacinaService: AnimalVacinaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.animalVacinaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'animalVacinaListModification',
        content: 'Deleted an animalVacina'
      });
      this.activeModal.dismiss(true);
    });
  }
}
