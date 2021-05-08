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
import { IAnexoAtendimento, AnexoAtendimento } from 'app/shared/model/anexo-atendimento.model';
import { AnexoAtendimentoService } from './anexo-atendimento.service';
import { IAtendimento } from 'app/shared/model/atendimento.model';
import { AtendimentoService } from 'app/entities/atendimento/atendimento.service';

@Component({
  selector: 'jhi-anexo-atendimento-update',
  templateUrl: './anexo-atendimento-update.component.html'
})
export class AnexoAtendimentoUpdateComponent implements OnInit {
  isSaving: boolean;

  atendimentos: IAtendimento[];

  editForm = this.fb.group({
    id: [],
    anexo: [],
    anexoContentType: [],
    descricao: [],
    data: [],
    url: [],
    urlThumbnail: [],
    atendimentoId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected anexoAtendimentoService: AnexoAtendimentoService,
    protected atendimentoService: AtendimentoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ anexoAtendimento }) => {
      this.updateForm(anexoAtendimento);
    });
    this.atendimentoService
      .query()
      .subscribe(
        (res: HttpResponse<IAtendimento[]>) => (this.atendimentos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(anexoAtendimento: IAnexoAtendimento) {
    this.editForm.patchValue({
      id: anexoAtendimento.id,
      anexo: anexoAtendimento.anexo,
      anexoContentType: anexoAtendimento.anexoContentType,
      descricao: anexoAtendimento.descricao,
      data: anexoAtendimento.data != null ? anexoAtendimento.data.format(DATE_TIME_FORMAT) : null,
      url: anexoAtendimento.url,
      urlThumbnail: anexoAtendimento.urlThumbnail,
      atendimentoId: anexoAtendimento.atendimentoId
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
    const anexoAtendimento = this.createFromForm();
    if (anexoAtendimento.id !== undefined) {
      this.subscribeToSaveResponse(this.anexoAtendimentoService.update(anexoAtendimento));
    } else {
      this.subscribeToSaveResponse(this.anexoAtendimentoService.create(anexoAtendimento));
    }
  }

  private createFromForm(): IAnexoAtendimento {
    return {
      ...new AnexoAtendimento(),
      id: this.editForm.get(['id']).value,
      anexoContentType: this.editForm.get(['anexoContentType']).value,
      anexo: this.editForm.get(['anexo']).value,
      descricao: this.editForm.get(['descricao']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      url: this.editForm.get(['url']).value,
      urlThumbnail: this.editForm.get(['urlThumbnail']).value,
      atendimentoId: this.editForm.get(['atendimentoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexoAtendimento>>) {
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

  trackAtendimentoById(index: number, item: IAtendimento) {
    return item.id;
  }
}
