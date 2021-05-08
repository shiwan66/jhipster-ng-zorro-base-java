import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAnimal } from 'app/shared/model/animal.model';

@Component({
  selector: 'jhi-animal-detail',
  templateUrl: './animal-detail.component.html'
})
export class AnimalDetailComponent implements OnInit {
  animal: IAnimal;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ animal }) => {
      this.animal = animal;
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
