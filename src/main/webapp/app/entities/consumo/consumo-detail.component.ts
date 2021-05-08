import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConsumo } from 'app/shared/model/consumo.model';

@Component({
  selector: 'jhi-consumo-detail',
  templateUrl: './consumo-detail.component.html'
})
export class ConsumoDetailComponent implements OnInit {
  consumo: IConsumo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ consumo }) => {
      this.consumo = consumo;
    });
  }

  previousState() {
    window.history.back();
  }
}
