import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IVendaConsumo, VendaConsumo } from 'app/shared/model/venda-consumo.model';
import { VendaConsumoService } from './venda-consumo.service';
import { IVenda } from 'app/shared/model/venda.model';
import { VendaService } from 'app/entities/venda/venda.service';
import { IConsumo } from 'app/shared/model/consumo.model';
import { ConsumoService } from 'app/entities/consumo/consumo.service';

@Component({
  selector: 'jhi-venda-consumo-update',
  templateUrl: './venda-consumo-update.component.html'
})
export class VendaConsumoUpdateComponent implements OnInit {
  isSaving: boolean;

  vendas: IVenda[];

  consumos: IConsumo[];

  editForm = this.fb.group({
    id: [],
    quantidade: [],
    valorUnitario: [],
    valorTotal: [],
    vendaId: [],
    consumoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected vendaConsumoService: VendaConsumoService,
    protected vendaService: VendaService,
    protected consumoService: ConsumoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vendaConsumo }) => {
      this.updateForm(vendaConsumo);
    });
    this.vendaService
      .query()
      .subscribe((res: HttpResponse<IVenda[]>) => (this.vendas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.consumoService
      .query()
      .subscribe((res: HttpResponse<IConsumo[]>) => (this.consumos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(vendaConsumo: IVendaConsumo) {
    this.editForm.patchValue({
      id: vendaConsumo.id,
      quantidade: vendaConsumo.quantidade,
      valorUnitario: vendaConsumo.valorUnitario,
      valorTotal: vendaConsumo.valorTotal,
      vendaId: vendaConsumo.vendaId,
      consumoId: vendaConsumo.consumoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vendaConsumo = this.createFromForm();
    if (vendaConsumo.id !== undefined) {
      this.subscribeToSaveResponse(this.vendaConsumoService.update(vendaConsumo));
    } else {
      this.subscribeToSaveResponse(this.vendaConsumoService.create(vendaConsumo));
    }
  }

  private createFromForm(): IVendaConsumo {
    return {
      ...new VendaConsumo(),
      id: this.editForm.get(['id']).value,
      quantidade: this.editForm.get(['quantidade']).value,
      valorUnitario: this.editForm.get(['valorUnitario']).value,
      valorTotal: this.editForm.get(['valorTotal']).value,
      vendaId: this.editForm.get(['vendaId']).value,
      consumoId: this.editForm.get(['consumoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVendaConsumo>>) {
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

  trackVendaById(index: number, item: IVenda) {
    return item.id;
  }

  trackConsumoById(index: number, item: IConsumo) {
    return item.id;
  }
}
