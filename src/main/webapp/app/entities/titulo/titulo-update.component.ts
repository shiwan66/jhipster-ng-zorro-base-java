import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ITitulo, Titulo } from 'app/shared/model/titulo.model';
import { TituloService } from './titulo.service';
import { ITutor } from 'app/shared/model/tutor.model';
import { TutorService } from 'app/entities/tutor/tutor.service';
import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from 'app/entities/fornecedor/fornecedor.service';

@Component({
  selector: 'jhi-titulo-update',
  templateUrl: './titulo-update.component.html'
})
export class TituloUpdateComponent implements OnInit {
  isSaving: boolean;

  tutors: ITutor[];

  fornecedors: IFornecedor[];
  dataEmissaoDp: any;
  dataPagamentoDp: any;
  dataVencimentoDp: any;

  editForm = this.fb.group({
    id: [],
    isPago: [],
    tipo: [],
    descricao: [],
    valor: [],
    dataEmissao: [],
    dataPagamento: [],
    dataVencimento: [],
    tutorId: [],
    fornecedorId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected tituloService: TituloService,
    protected tutorService: TutorService,
    protected fornecedorService: FornecedorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ titulo }) => {
      this.updateForm(titulo);
    });
    this.tutorService
      .query()
      .subscribe((res: HttpResponse<ITutor[]>) => (this.tutors = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.fornecedorService
      .query()
      .subscribe(
        (res: HttpResponse<IFornecedor[]>) => (this.fornecedors = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(titulo: ITitulo) {
    this.editForm.patchValue({
      id: titulo.id,
      isPago: titulo.isPago,
      tipo: titulo.tipo,
      descricao: titulo.descricao,
      valor: titulo.valor,
      dataEmissao: titulo.dataEmissao,
      dataPagamento: titulo.dataPagamento,
      dataVencimento: titulo.dataVencimento,
      tutorId: titulo.tutorId,
      fornecedorId: titulo.fornecedorId
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
    const titulo = this.createFromForm();
    if (titulo.id !== undefined) {
      this.subscribeToSaveResponse(this.tituloService.update(titulo));
    } else {
      this.subscribeToSaveResponse(this.tituloService.create(titulo));
    }
  }

  private createFromForm(): ITitulo {
    return {
      ...new Titulo(),
      id: this.editForm.get(['id']).value,
      isPago: this.editForm.get(['isPago']).value,
      tipo: this.editForm.get(['tipo']).value,
      descricao: this.editForm.get(['descricao']).value,
      valor: this.editForm.get(['valor']).value,
      dataEmissao: this.editForm.get(['dataEmissao']).value,
      dataPagamento: this.editForm.get(['dataPagamento']).value,
      dataVencimento: this.editForm.get(['dataVencimento']).value,
      tutorId: this.editForm.get(['tutorId']).value,
      fornecedorId: this.editForm.get(['fornecedorId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITitulo>>) {
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

  trackTutorById(index: number, item: ITutor) {
    return item.id;
  }

  trackFornecedorById(index: number, item: IFornecedor) {
    return item.id;
  }
}
