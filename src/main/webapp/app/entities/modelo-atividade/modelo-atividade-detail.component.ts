import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModeloAtividade } from 'app/shared/model/modelo-atividade.model';

@Component({
  selector: 'jhi-modelo-atividade-detail',
  templateUrl: './modelo-atividade-detail.component.html'
})
export class ModeloAtividadeDetailComponent implements OnInit {
  modeloAtividade: IModeloAtividade;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ modeloAtividade }) => {
      this.modeloAtividade = modeloAtividade;
    });
  }

  previousState() {
    window.history.back();
  }
}
