import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnimalVeterinario } from 'app/shared/model/animal-veterinario.model';

@Component({
  selector: 'jhi-animal-veterinario-detail',
  templateUrl: './animal-veterinario-detail.component.html'
})
export class AnimalVeterinarioDetailComponent implements OnInit {
  animalVeterinario: IAnimalVeterinario;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ animalVeterinario }) => {
      this.animalVeterinario = animalVeterinario;
    });
  }

  previousState() {
    window.history.back();
  }
}
