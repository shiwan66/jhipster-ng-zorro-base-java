import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAnexoAtendimento } from 'app/shared/model/anexo-atendimento.model';

@Component({
  selector: 'jhi-anexo-atendimento-detail',
  templateUrl: './anexo-atendimento-detail.component.html'
})
export class AnexoAtendimentoDetailComponent implements OnInit {
  anexoAtendimento: IAnexoAtendimento;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ anexoAtendimento }) => {
      this.anexoAtendimento = anexoAtendimento;
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
