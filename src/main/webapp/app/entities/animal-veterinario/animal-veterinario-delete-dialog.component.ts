import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnimalVeterinario } from 'app/shared/model/animal-veterinario.model';
import { AnimalVeterinarioService } from './animal-veterinario.service';

@Component({
  templateUrl: './animal-veterinario-delete-dialog.component.html'
})
export class AnimalVeterinarioDeleteDialogComponent {
  animalVeterinario: IAnimalVeterinario;

  constructor(
    protected animalVeterinarioService: AnimalVeterinarioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.animalVeterinarioService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'animalVeterinarioListModification',
        content: 'Deleted an animalVeterinario'
      });
      this.activeModal.dismiss(true);
    });
  }
}
