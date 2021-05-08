import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAnimalObservacao } from 'app/shared/model/animal-observacao.model';

@Component({
  selector: 'jhi-animal-observacao-detail',
  templateUrl: './animal-observacao-detail.component.html'
})
export class AnimalObservacaoDetailComponent implements OnInit {
  animalObservacao: IAnimalObservacao;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ animalObservacao }) => {
      this.animalObservacao = animalObservacao;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
