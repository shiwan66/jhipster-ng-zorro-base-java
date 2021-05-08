import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnimalCarrapaticida } from 'app/shared/model/animal-carrapaticida.model';

@Component({
  selector: 'jhi-animal-carrapaticida-detail',
  templateUrl: './animal-carrapaticida-detail.component.html'
})
export class AnimalCarrapaticidaDetailComponent implements OnInit {
  animalCarrapaticida: IAnimalCarrapaticida;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ animalCarrapaticida }) => {
      this.animalCarrapaticida = animalCarrapaticida;
    });
  }

  previousState() {
    window.history.back();
  }
}
