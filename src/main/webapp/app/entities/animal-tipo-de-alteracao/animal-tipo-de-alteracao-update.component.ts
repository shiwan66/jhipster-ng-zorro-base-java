import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAnimalTipoDeAlteracao, AnimalTipoDeAlteracao } from 'app/shared/model/animal-tipo-de-alteracao.model';
import { AnimalTipoDeAlteracaoService } from './animal-tipo-de-alteracao.service';

@Component({
  selector: 'jhi-animal-tipo-de-alteracao-update',
  templateUrl: './animal-tipo-de-alteracao-update.component.html'
})
export class AnimalTipoDeAlteracaoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    descricao: []
  });

  constructor(
    protected animalTipoDeAlteracaoService: AnimalTipoDeAlteracaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ animalTipoDeAlteracao }) => {
      this.updateForm(animalTipoDeAlteracao);
    });
  }

  updateForm(animalTipoDeAlteracao: IAnimalTipoDeAlteracao) {
    this.editForm.patchValue({
      id: animalTipoDeAlteracao.id,
      descricao: animalTipoDeAlteracao.descricao
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const animalTipoDeAlteracao = this.createFromForm();
    if (animalTipoDeAlteracao.id !== undefined) {
      this.subscribeToSaveResponse(this.animalTipoDeAlteracaoService.update(animalTipoDeAlteracao));
    } else {
      this.subscribeToSaveResponse(this.animalTipoDeAlteracaoService.create(animalTipoDeAlteracao));
    }
  }

  private createFromForm(): IAnimalTipoDeAlteracao {
    return {
      ...new AnimalTipoDeAlteracao(),
      id: this.editForm.get(['id']).value,
      descricao: this.editForm.get(['descricao']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnimalTipoDeAlteracao>>) {
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
