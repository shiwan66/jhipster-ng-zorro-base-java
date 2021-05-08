import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnimalVermifugo } from 'app/shared/model/animal-vermifugo.model';

@Component({
  selector: 'jhi-animal-vermifugo-detail',
  templateUrl: './animal-vermifugo-detail.component.html'
})
export class AnimalVermifugoDetailComponent implements OnInit {
  animalVermifugo: IAnimalVermifugo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ animalVermifugo }) => {
      this.animalVermifugo = animalVermifugo;
    });
  }

  previousState() {
    window.history.back();
  }
}
