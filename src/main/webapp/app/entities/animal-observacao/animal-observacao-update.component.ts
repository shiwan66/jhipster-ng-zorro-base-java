import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAnimalObservacao, AnimalObservacao } from 'app/shared/model/animal-observacao.model';
import { AnimalObservacaoService } from './animal-observacao.service';
import { IAnimal } from 'app/shared/model/animal.model';
import { AnimalService } from 'app/entities/animal/animal.service';

@Component({
  selector: 'jhi-animal-observacao-update',
  templateUrl: './animal-observacao-update.component.html'
})
export class AnimalObservacaoUpdateComponent implements OnInit {
  isSaving: boolean;

  animals: IAnimal[];
  dataAlteracaoDp: any;

  editForm = this.fb.group({
    id: [],
    dataAlteracao: [],
    observacao: [],
    animalId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected animalObservacaoService: AnimalObservacaoService,
    protected animalService: AnimalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ animalObservacao }) => {
      this.updateForm(animalObservacao);
    });
    this.animalService
      .query()
      .subscribe((res: HttpResponse<IAnimal[]>) => (this.animals = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(animalObservacao: IAnimalObservacao) {
    this.editForm.patchValue({
      id: animalObservacao.id,
      dataAlteracao: animalObservacao.dataAlteracao,
      observacao: animalObservacao.observacao,
      animalId: animalObservacao.animalId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const animalObservacao = this.createFromForm();
    if (animalObservacao.id !== undefined) {
      this.subscribeToSaveResponse(this.animalObservacaoService.update(animalObservacao));
    } else {
      this.subscribeToSaveResponse(this.animalObservacaoService.create(animalObservacao));
    }
  }

  private createFromForm(): IAnimalObservacao {
    return {
      ...new AnimalObservacao(),
      id: this.editForm.get(['id']).value,
      dataAlteracao: this.editForm.get(['dataAlteracao']).value,
      observacao: this.editForm.get(['observacao']).value,
      animalId: this.editForm.get(['animalId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnimalObservacao>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAnimalById(index: number, item: IAnimal) {
    return item.id;
  }
}
