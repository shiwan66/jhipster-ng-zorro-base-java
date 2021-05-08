import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnimalTipoDeVacina } from 'app/shared/model/animal-tipo-de-vacina.model';

@Component({
  selector: 'jhi-animal-tipo-de-vacina-detail',
  templateUrl: './animal-tipo-de-vacina-detail.component.html'
})
export class AnimalTipoDeVacinaDetailComponent implements OnInit {
  animalTipoDeVacina: IAnimalTipoDeVacina;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ animalTipoDeVacina }) => {
      this.animalTipoDeVacina = animalTipoDeVacina;
    });
  }

  previousState() {
    window.history.back();
  }
}
