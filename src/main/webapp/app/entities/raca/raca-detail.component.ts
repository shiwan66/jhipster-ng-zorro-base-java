import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRaca } from 'app/shared/model/raca.model';

@Component({
  selector: 'jhi-raca-detail',
  templateUrl: './raca-detail.component.html'
})
export class RacaDetailComponent implements OnInit {
  raca: IRaca;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ raca }) => {
      this.raca = raca;
    });
  }

  previousState() {
    window.history.back();
  }
}
