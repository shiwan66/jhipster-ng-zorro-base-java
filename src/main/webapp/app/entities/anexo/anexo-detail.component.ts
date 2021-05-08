import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAnexo } from 'app/shared/model/anexo.model';

@Component({
  selector: 'jhi-anexo-detail',
  templateUrl: './anexo-detail.component.html'
})
export class AnexoDetailComponent implements OnInit {
  anexo: IAnexo;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ anexo }) => {
      this.anexo = anexo;
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
