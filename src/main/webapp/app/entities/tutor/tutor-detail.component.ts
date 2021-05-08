import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITutor } from 'app/shared/model/tutor.model';

@Component({
  selector: 'jhi-tutor-detail',
  templateUrl: './tutor-detail.component.html'
})
export class TutorDetailComponent implements OnInit {
  tutor: ITutor;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tutor }) => {
      this.tutor = tutor;
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
