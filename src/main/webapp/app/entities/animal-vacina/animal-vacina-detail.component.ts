import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnimalVacina } from 'app/shared/model/animal-vacina.model';

@Component({
  selector: 'jhi-animal-vacina-detail',
  templateUrl: './animal-vacina-detail.component.html'
})
export class AnimalVacinaDetailComponent implements OnInit {
  animalVacina: IAnimalVacina;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ animalVacina }) => {
      this.animalVacina = animalVacina;
    });
  }

  previousState() {
    window.history.back();
  }
}
