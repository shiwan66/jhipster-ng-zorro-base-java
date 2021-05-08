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
import { IVenda, Venda } from 'app/shared/model/venda.model';
import { VendaService } from './venda.service';
import { IAtendimento } from 'app/shared/model/atendimento.model';
import { AtendimentoService } from 'app/entities/atendimento/atendimento.service';

@Component({
  selector: 'jhi-venda-update',
  templateUrl: './venda-update.component.html'
})
export class VendaUpdateComponent implements OnInit {
  isSaving: boolean;

  atendimentos: IAtendimento[];

  editForm = this.fb.group({
    id: [],
    observacao: [],
    dataDaCompra: [],
    dataDoPagamento: [],
    desconto: [],
    situacao: [],
    valorTotal: [],
    atendimentoId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected vendaService: VendaService,
    protected atendimentoService: AtendimentoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ venda }) => {
      this.updateForm(venda);
    });
    this.atendimentoService
      .query()
      .subscribe(
        (res: HttpResponse<IAtendimento[]>) => (this.atendimentos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(venda: IVenda) {
    this.editForm.patchValue({
      id: venda.id,
      observacao: venda.observacao,
      dataDaCompra: venda.dataDaCompra != null ? venda.dataDaCompra.format(DATE_TIME_FORMAT) : null,
      dataDoPagamento: venda.dataDoPagamento != null ? venda.dataDoPagamento.format(DATE_TIME_FORMAT) : null,
      desconto: venda.desconto,
      situacao: venda.situacao,
      valorTotal: venda.valorTotal,
      atendimentoId: venda.atendimentoId
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
    const venda = this.createFromForm();
    if (venda.id !== undefined) {
      this.subscribeToSaveResponse(this.vendaService.update(venda));
    } else {
      this.subscribeToSaveResponse(this.vendaService.create(venda));
    }
  }

  private createFromForm(): IVenda {
    return {
      ...new Venda(),
      id: this.editForm.get(['id']).value,
      observacao: this.editForm.get(['observacao']).value,
      dataDaCompra:
        this.editForm.get(['dataDaCompra']).value != null ? moment(this.editForm.get(['dataDaCompra']).value, DATE_TIME_FORMAT) : undefined,
      dataDoPagamento:
        this.editForm.get(['dataDoPagamento']).value != null
          ? moment(this.editForm.get(['dataDoPagamento']).value, DATE_TIME_FORMAT)
          : undefined,
      desconto: this.editForm.get(['desconto']).value,
      situacao: this.editForm.get(['situacao']).value,
      valorTotal: this.editForm.get(['valorTotal']).value,
      atendimentoId: this.editForm.get(['atendimentoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenda>>) {
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
