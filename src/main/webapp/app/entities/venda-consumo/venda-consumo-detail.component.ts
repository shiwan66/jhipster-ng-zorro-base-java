import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVendaConsumo } from 'app/shared/model/venda-consumo.model';

@Component({
  selector: 'jhi-venda-consumo-detail',
  templateUrl: './venda-consumo-detail.component.html'
})
export class VendaConsumoDetailComponent implements OnInit {
  vendaConsumo: IVendaConsumo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vendaConsumo }) => {
      this.vendaConsumo = vendaConsumo;
    });
  }

  previousState() {
    window.history.back();
  }
}
