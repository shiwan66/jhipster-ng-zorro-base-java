import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnimalCarrapaticida } from 'app/shared/model/animal-carrapaticida.model';
import { AnimalCarrapaticidaService } from './animal-carrapaticida.service';

@Component({
  templateUrl: './animal-carrapaticida-delete-dialog.component.html'
})
export class AnimalCarrapaticidaDeleteDialogComponent {
  animalCarrapaticida: IAnimalCarrapaticida;

  constructor(
    protected animalCarrapaticidaService: AnimalCarrapaticidaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.animalCarrapaticidaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'animalCarrapaticidaListModification',
        content: 'Deleted an animalCarrapaticida'
      });
      this.activeModal.dismiss(true);
    });
  }
}
