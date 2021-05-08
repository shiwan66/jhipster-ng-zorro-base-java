import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnimalTipoDeAlteracao } from 'app/shared/model/animal-tipo-de-alteracao.model';

@Component({
  selector: 'jhi-animal-tipo-de-alteracao-detail',
  templateUrl: './animal-tipo-de-alteracao-detail.component.html'
})
export class AnimalTipoDeAlteracaoDetailComponent implements OnInit {
  animalTipoDeAlteracao: IAnimalTipoDeAlteracao;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ animalTipoDeAlteracao }) => {
      this.animalTipoDeAlteracao = animalTipoDeAlteracao;
    });
  }

  previousState() {
    window.history.back();
  }
}
