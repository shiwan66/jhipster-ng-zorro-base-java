import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IConsumo, Consumo } from 'app/shared/model/consumo.model';
import { ConsumoService } from './consumo.service';

@Component({
  selector: 'jhi-consumo-update',
  templateUrl: './consumo-update.component.html'
})
export class ConsumoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [],
    tipo: [],
    estoque: [],
    valorUnitario: []
  });

  constructor(protected consumoService: ConsumoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ consumo }) => {
      this.updateForm(consumo);
    });
  }

  updateForm(consumo: IConsumo) {
    this.editForm.patchValue({
      id: consumo.id,
      nome: consumo.nome,
      tipo: consumo.tipo,
      estoque: consumo.estoque,
      valorUnitario: consumo.valorUnitario
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const consumo = this.createFromForm();
    if (consumo.id !== undefined) {
      this.subscribeToSaveResponse(this.consumoService.update(consumo));
    } else {
      this.subscribeToSaveResponse(this.consumoService.create(consumo));
    }
  }

  private createFromForm(): IConsumo {
    return {
      ...new Consumo(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      tipo: this.editForm.get(['tipo']).value,
      estoque: this.editForm.get(['estoque']).value,
      valorUnitario: this.editForm.get(['valorUnitario']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConsumo>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
