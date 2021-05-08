import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEndereco, Endereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from './endereco.service';

@Component({
  selector: 'jhi-endereco-update',
  templateUrl: './endereco-update.component.html'
})
export class EnderecoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    cep: [],
    logradouro: [],
    numero: [],
    complemento: [],
    bairro: [],
    localidade: [],
    uf: [],
    ibge: []
  });

  constructor(protected enderecoService: EnderecoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ endereco }) => {
      this.updateForm(endereco);
    });
  }

  updateForm(endereco: IEndereco) {
    this.editForm.patchValue({
      id: endereco.id,
      cep: endereco.cep,
      logradouro: endereco.logradouro,
      numero: endereco.numero,
      complemento: endereco.complemento,
      bairro: endereco.bairro,
      localidade: endereco.localidade,
      uf: endereco.uf,
      ibge: endereco.ibge
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const endereco = this.createFromForm();
    if (endereco.id !== undefined) {
      this.subscribeToSaveResponse(this.enderecoService.update(endereco));
    } else {
      this.subscribeToSaveResponse(this.enderecoService.create(endereco));
    }
  }

  private createFromForm(): IEndereco {
    return {
      ...new Endereco(),
      id: this.editForm.get(['id']).value,
      cep: this.editForm.get(['cep']).value,
      logradouro: this.editForm.get(['logradouro']).value,
      numero: this.editForm.get(['numero']).value,
      complemento: this.editForm.get(['complemento']).value,
      bairro: this.editForm.get(['bairro']).value,
      localidade: this.editForm.get(['localidade']).value,
      uf: this.editForm.get(['uf']).value,
      ibge: this.editForm.get(['ibge']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEndereco>>) {
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
