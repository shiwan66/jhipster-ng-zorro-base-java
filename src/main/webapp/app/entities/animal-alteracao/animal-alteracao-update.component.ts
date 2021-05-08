import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAnimalAlteracao, AnimalAlteracao } from 'app/shared/model/animal-alteracao.model';
import { AnimalAlteracaoService } from './animal-alteracao.service';
import { IAnimalTipoDeAlteracao } from 'app/shared/model/animal-tipo-de-alteracao.model';
import { AnimalTipoDeAlteracaoService } from 'app/entities/animal-tipo-de-alteracao/animal-tipo-de-alteracao.service';
import { IAnimal } from 'app/shared/model/animal.model';
import { AnimalService } from 'app/entities/animal/animal.service';

@Component({
  selector: 'jhi-animal-alteracao-update',
  templateUrl: './animal-alteracao-update.component.html'
})
export class AnimalAlteracaoUpdateComponent implements OnInit {
  isSaving: boolean;

  animaltipodealteracaos: IAnimalTipoDeAlteracao[];

  animals: IAnimal[];
  dataAlteracaoDp: any;

  editForm = this.fb.group({
    id: [],
    descricao: [],
    dataAlteracao: [],
    animalTipoDeAlteracaoId: [],
    animalId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected animalAlteracaoService: AnimalAlteracaoService,
    protected animalTipoDeAlteracaoService: AnimalTipoDeAlteracaoService,
    protected animalService: AnimalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ animalAlteracao }) => {
      this.updateForm(animalAlteracao);
    });
    this.animalTipoDeAlteracaoService
      .query()
      .subscribe(
        (res: HttpResponse<IAnimalTipoDeAlteracao[]>) => (this.animaltipodealteracaos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.animalService
      .query()
      .subscribe((res: HttpResponse<IAnimal[]>) => (this.animals = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(animalAlteracao: IAnimalAlteracao) {
    this.editForm.patchValue({
      id: animalAlteracao.id,
      descricao: animalAlteracao.descricao,
      dataAlteracao: animalAlteracao.dataAlteracao,
      animalTipoDeAlteracaoId: animalAlteracao.animalTipoDeAlteracaoId,
      animalId: animalAlteracao.animalId
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
    const animalAlteracao = this.createFromForm();
    if (animalAlteracao.id !== undefined) {
      this.subscribeToSaveResponse(this.animalAlteracaoService.update(animalAlteracao));
    } else {
      this.subscribeToSaveResponse(this.animalAlteracaoService.create(animalAlteracao));
    }
  }

  private createFromForm(): IAnimalAlteracao {
    return {
      ...new AnimalAlteracao(),
      id: this.editForm.get(['id']).value,
      descricao: this.editForm.get(['descricao']).value,
      dataAlteracao: this.editForm.get(['dataAlteracao']).value,
      animalTipoDeAlteracaoId: this.editForm.get(['animalTipoDeAlteracaoId']).value,
      animalId: this.editForm.get(['animalId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnimalAlteracao>>) {
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

  trackAnimalTipoDeAlteracaoById(index: number, item: IAnimalTipoDeAlteracao) {
    return item.id;
  }

  trackAnimalById(index: number, item: IAnimal) {
    return item.id;
  }
}
