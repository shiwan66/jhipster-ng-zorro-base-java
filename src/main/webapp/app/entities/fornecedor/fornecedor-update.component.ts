import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IFornecedor, Fornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from './fornecedor.service';
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco/endereco.service';

@Component({
  selector: 'jhi-fornecedor-update',
  templateUrl: './fornecedor-update.component.html'
})
export class FornecedorUpdateComponent implements OnInit {
  isSaving: boolean;

  enderecos: IEndereco[];

  editForm = this.fb.group({
    id: [],
    nome: [],
    telefone: [],
    email: [],
    pontoReferencia: [],
    enderecoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected fornecedorService: FornecedorService,
    protected enderecoService: EnderecoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fornecedor }) => {
      this.updateForm(fornecedor);
    });
    this.enderecoService
      .query()
      .subscribe((res: HttpResponse<IEndereco[]>) => (this.enderecos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(fornecedor: IFornecedor) {
    this.editForm.patchValue({
      id: fornecedor.id,
      nome: fornecedor.nome,
      telefone: fornecedor.telefone,
      email: fornecedor.email,
      pontoReferencia: fornecedor.pontoReferencia,
      enderecoId: fornecedor.enderecoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fornecedor = this.createFromForm();
    if (fornecedor.id !== undefined) {
      this.subscribeToSaveResponse(this.fornecedorService.update(fornecedor));
    } else {
      this.subscribeToSaveResponse(this.fornecedorService.create(fornecedor));
    }
  }

  private createFromForm(): IFornecedor {
    return {
      ...new Fornecedor(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      telefone: this.editForm.get(['telefone']).value,
      email: this.editForm.get(['email']).value,
      pontoReferencia: this.editForm.get(['pontoReferencia']).value,
      enderecoId: this.editForm.get(['enderecoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFornecedor>>) {
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

  trackEnderecoById(index: number, item: IEndereco) {
    return item.id;
  }
}
