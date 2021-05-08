import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IMovimentacaoDeEstoque } from 'app/shared/model/movimentacao-de-estoque.model';

@Component({
  selector: 'jhi-movimentacao-de-estoque-detail',
  templateUrl: './movimentacao-de-estoque-detail.component.html'
})
export class MovimentacaoDeEstoqueDetailComponent implements OnInit {
  movimentacaoDeEstoque: IMovimentacaoDeEstoque;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ movimentacaoDeEstoque }) => {
      this.movimentacaoDeEstoque = movimentacaoDeEstoque;
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
