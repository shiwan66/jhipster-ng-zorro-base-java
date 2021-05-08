import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnimal } from 'app/shared/model/animal.model';
import { AnimalService } from './animal.service';

@Component({
  templateUrl: './animal-delete-dialog.component.html'
})
export class AnimalDeleteDialogComponent {
  animal: IAnimal;

  constructor(protected animalService: AnimalService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.animalService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'animalListModification',
        content: 'Deleted an animal'
      });
      this.activeModal.dismiss(true);
    });
  }
}
