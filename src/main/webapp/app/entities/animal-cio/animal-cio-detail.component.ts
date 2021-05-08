import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAnimalCio } from 'app/shared/model/animal-cio.model';

@Component({
  selector: 'jhi-animal-cio-detail',
  templateUrl: './animal-cio-detail.component.html'
})
export class AnimalCioDetailComponent implements OnInit {
  animalCio: IAnimalCio;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ animalCio }) => {
      this.animalCio = animalCio;
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
