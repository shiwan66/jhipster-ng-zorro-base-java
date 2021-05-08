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
import { IMovimentacaoDeEstoque, MovimentacaoDeEstoque } from 'app/shared/model/movimentacao-de-estoque.model';
import { MovimentacaoDeEstoqueService } from './movimentacao-de-estoque.service';
import { IConsumo } from 'app/shared/model/consumo.model';
import { ConsumoService } from 'app/entities/consumo/consumo.service';

@Component({
  selector: 'jhi-movimentacao-de-estoque-update',
  templateUrl: './movimentacao-de-estoque-update.component.html'
})
export class MovimentacaoDeEstoqueUpdateComponent implements OnInit {
  isSaving: boolean;

  consumos: IConsumo[];

  editForm = this.fb.group({
    id: [],
    tipo: [],
    descricao: [],
    data: [],
    quantidade: [],
    consumoId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected movimentacaoDeEstoqueService: MovimentacaoDeEstoqueService,
    protected consumoService: ConsumoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ movimentacaoDeEstoque }) => {
      this.updateForm(movimentacaoDeEstoque);
    });
    this.consumoService
      .query()
      .subscribe((res: HttpResponse<IConsumo[]>) => (this.consumos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(movimentacaoDeEstoque: IMovimentacaoDeEstoque) {
    this.editForm.patchValue({
      id: movimentacaoDeEstoque.id,
      tipo: movimentacaoDeEstoque.tipo,
      descricao: movimentacaoDeEstoque.descricao,
      data: movimentacaoDeEstoque.data != null ? movimentacaoDeEstoque.data.format(DATE_TIME_FORMAT) : null,
      quantidade: movimentacaoDeEstoque.quantidade,
      consumoId: movimentacaoDeEstoque.consumoId
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
    const movimentacaoDeEstoque = this.createFromForm();
    if (movimentacaoDeEstoque.id !== undefined) {
      this.subscribeToSaveResponse(this.movimentacaoDeEstoqueService.update(movimentacaoDeEstoque));
    } else {
      this.subscribeToSaveResponse(this.movimentacaoDeEstoqueService.create(movimentacaoDeEstoque));
    }
  }

  private createFromForm(): IMovimentacaoDeEstoque {
    return {
      ...new MovimentacaoDeEstoque(),
      id: this.editForm.get(['id']).value,
      tipo: this.editForm.get(['tipo']).value,
      descricao: this.editForm.get(['descricao']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      quantidade: this.editForm.get(['quantidade']).value,
      consumoId: this.editForm.get(['consumoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovimentacaoDeEstoque>>) {
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

  trackConsumoById(index: number, item: IConsumo) {
    return item.id;
  }
}
