import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnimalVermifugo } from 'app/shared/model/animal-vermifugo.model';
import { AnimalVermifugoService } from './animal-vermifugo.service';

@Component({
  templateUrl: './animal-vermifugo-delete-dialog.component.html'
})
export class AnimalVermifugoDeleteDialogComponent {
  animalVermifugo: IAnimalVermifugo;

  constructor(
    protected animalVermifugoService: AnimalVermifugoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.animalVermifugoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'animalVermifugoListModification',
        content: 'Deleted an animalVermifugo'
      });
      this.activeModal.dismiss(true);
    });
  }
}
