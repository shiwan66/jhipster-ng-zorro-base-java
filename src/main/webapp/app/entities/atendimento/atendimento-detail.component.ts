import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAtendimento } from 'app/shared/model/atendimento.model';

@Component({
  selector: 'jhi-atendimento-detail',
  templateUrl: './atendimento-detail.component.html'
})
export class AtendimentoDetailComponent implements OnInit {
  atendimento: IAtendimento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ atendimento }) => {
      this.atendimento = atendimento;
    });
  }

  previousState() {
    window.history.back();
  }
}
