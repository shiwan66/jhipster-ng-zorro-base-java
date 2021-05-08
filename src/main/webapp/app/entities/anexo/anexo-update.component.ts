import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAnexo, Anexo } from 'app/shared/model/anexo.model';
import { AnexoService } from './anexo.service';
import { IAnimal } from 'app/shared/model/animal.model';
import { AnimalService } from 'app/entities/animal/animal.service';

@Component({
  selector: 'jhi-anexo-update',
  templateUrl: './anexo-update.component.html'
})
export class AnexoUpdateComponent implements OnInit {
  isSaving: boolean;

  animals: IAnimal[];

  editForm = this.fb.group({
    id: [],
    anexo: [],
    anexoContentType: [],
    descricao: [],
    data: [],
    url: [],
    urlThumbnail: [],
    animalId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected anexoService: AnexoService,
    protected animalService: AnimalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ anexo }) => {
      this.updateForm(anexo);
    });
    this.animalService
      .query()
      .subscribe((res: HttpResponse<IAnimal[]>) => (this.animals = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(anexo: IAnexo) {
    this.editForm.patchValue({
      id: anexo.id,
      anexo: anexo.anexo,
      anexoContentType: anexo.anexoContentType,
      descricao: anexo.descricao,
      data: anexo.data != null ? anexo.data.format(DATE_TIME_FORMAT) : null,
      url: anexo.url,
      urlThumbnail: anexo.urlThumbnail,
      animalId: anexo.animalId
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
    const anexo = this.createFromForm();
    if (anexo.id !== undefined) {
      this.subscribeToSaveResponse(this.anexoService.update(anexo));
    } else {
      this.subscribeToSaveResponse(this.anexoService.create(anexo));
    }
  }

  private createFromForm(): IAnexo {
    return {
      ...new Anexo(),
      id: this.editForm.get(['id']).value,
      anexoContentType: this.editForm.get(['anexoContentType']).value,
      anexo: this.editForm.get(['anexo']).value,
      descricao: this.editForm.get(['descricao']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      url: this.editForm.get(['url']).value,
      urlThumbnail: this.editForm.get(['urlThumbnail']).value,
      animalId: this.editForm.get(['animalId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexo>>) {
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
