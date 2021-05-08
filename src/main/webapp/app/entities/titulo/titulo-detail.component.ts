import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITitulo } from 'app/shared/model/titulo.model';

@Component({
  selector: 'jhi-titulo-detail',
  templateUrl: './titulo-detail.component.html'
})
export class TituloDetailComponent implements OnInit {
  titulo: ITitulo;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ titulo }) => {
      this.titulo = titulo;
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
