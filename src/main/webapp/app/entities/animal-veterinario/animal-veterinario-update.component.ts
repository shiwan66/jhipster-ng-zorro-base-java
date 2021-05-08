import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAnimalVeterinario, AnimalVeterinario } from 'app/shared/model/animal-veterinario.model';
import { AnimalVeterinarioService } from './animal-veterinario.service';

@Component({
  selector: 'jhi-animal-veterinario-update',
  templateUrl: './animal-veterinario-update.component.html'
})
export class AnimalVeterinarioUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [],
    telefone: [],
    clinica: []
  });

  constructor(
    protected animalVeterinarioService: AnimalVeterinarioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ animalVeterinario }) => {
      this.updateForm(animalVeterinario);
    });
  }

  updateForm(animalVeterinario: IAnimalVeterinario) {
    this.editForm.patchValue({
      id: animalVeterinario.id,
      nome: animalVeterinario.nome,
      telefone: animalVeterinario.telefone,
      clinica: animalVeterinario.clinica
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const animalVeterinario = this.createFromForm();
    if (animalVeterinario.id !== undefined) {
      this.subscribeToSaveResponse(this.animalVeterinarioService.update(animalVeterinario));
    } else {
      this.subscribeToSaveResponse(this.animalVeterinarioService.create(animalVeterinario));
    }
  }

  private createFromForm(): IAnimalVeterinario {
    return {
      ...new AnimalVeterinario(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      telefone: this.editForm.get(['telefone']).value,
      clinica: this.editForm.get(['clinica']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnimalVeterinario>>) {
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
