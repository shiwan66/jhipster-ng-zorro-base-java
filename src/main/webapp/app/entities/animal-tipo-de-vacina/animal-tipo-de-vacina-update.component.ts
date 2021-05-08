import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAnimalTipoDeVacina, AnimalTipoDeVacina } from 'app/shared/model/animal-tipo-de-vacina.model';
import { AnimalTipoDeVacinaService } from './animal-tipo-de-vacina.service';

@Component({
  selector: 'jhi-animal-tipo-de-vacina-update',
  templateUrl: './animal-tipo-de-vacina-update.component.html'
})
export class AnimalTipoDeVacinaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    descricao: []
  });

  constructor(
    protected animalTipoDeVacinaService: AnimalTipoDeVacinaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ animalTipoDeVacina }) => {
      this.updateForm(animalTipoDeVacina);
    });
  }

  updateForm(animalTipoDeVacina: IAnimalTipoDeVacina) {
    this.editForm.patchValue({
      id: animalTipoDeVacina.id,
      descricao: animalTipoDeVacina.descricao
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const animalTipoDeVacina = this.createFromForm();
    if (animalTipoDeVacina.id !== undefined) {
      this.subscribeToSaveResponse(this.animalTipoDeVacinaService.update(animalTipoDeVacina));
    } else {
      this.subscribeToSaveResponse(this.animalTipoDeVacinaService.create(animalTipoDeVacina));
    }
  }

  private createFromForm(): IAnimalTipoDeVacina {
    return {
      ...new AnimalTipoDeVacina(),
      id: this.editForm.get(['id']).value,
      descricao: this.editForm.get(['descricao']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnimalTipoDeVacina>>) {
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
