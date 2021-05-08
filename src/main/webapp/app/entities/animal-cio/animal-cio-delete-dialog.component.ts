import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnimalCio } from 'app/shared/model/animal-cio.model';
import { AnimalCioService } from './animal-cio.service';

@Component({
  templateUrl: './animal-cio-delete-dialog.component.html'
})
export class AnimalCioDeleteDialogComponent {
  animalCio: IAnimalCio;

  constructor(protected animalCioService: AnimalCioService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.animalCioService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'animalCioListModification',
        content: 'Deleted an animalCio'
      });
      this.activeModal.dismiss(true);
    });
  }
}
