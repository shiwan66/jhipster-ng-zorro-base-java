import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAnimalAlteracao } from 'app/shared/model/animal-alteracao.model';

@Component({
  selector: 'jhi-animal-alteracao-detail',
  templateUrl: './animal-alteracao-detail.component.html'
})
export class AnimalAlteracaoDetailComponent implements OnInit {
  animalAlteracao: IAnimalAlteracao;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ animalAlteracao }) => {
      this.animalAlteracao = animalAlteracao;
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
