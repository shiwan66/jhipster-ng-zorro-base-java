import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAnimalCarrapaticida, AnimalCarrapaticida } from 'app/shared/model/animal-carrapaticida.model';
import { AnimalCarrapaticidaService } from './animal-carrapaticida.service';
import { IAnimal } from 'app/shared/model/animal.model';
import { AnimalService } from 'app/entities/animal/animal.service';

@Component({
  selector: 'jhi-animal-carrapaticida-update',
  templateUrl: './animal-carrapaticida-update.component.html'
})
export class AnimalCarrapaticidaUpdateComponent implements OnInit {
  isSaving: boolean;

  animals: IAnimal[];
  dataAplicacaoDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [],
    dataAplicacao: [],
    animalId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected animalCarrapaticidaService: AnimalCarrapaticidaService,
    protected animalService: AnimalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ animalCarrapaticida }) => {
      this.updateForm(animalCarrapaticida);
    });
    this.animalService
      .query()
      .subscribe((res: HttpResponse<IAnimal[]>) => (this.animals = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(animalCarrapaticida: IAnimalCarrapaticida) {
    this.editForm.patchValue({
      id: animalCarrapaticida.id,
      nome: animalCarrapaticida.nome,
      dataAplicacao: animalCarrapaticida.dataAplicacao,
      animalId: animalCarrapaticida.animalId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const animalCarrapaticida = this.createFromForm();
    if (animalCarrapaticida.id !== undefined) {
      this.subscribeToSaveResponse(this.animalCarrapaticidaService.update(animalCarrapaticida));
    } else {
      this.subscribeToSaveResponse(this.animalCarrapaticidaService.create(animalCarrapaticida));
    }
  }

  private createFromForm(): IAnimalCarrapaticida {
    return {
      ...new AnimalCarrapaticida(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      dataAplicacao: this.editForm.get(['dataAplicacao']).value,
      animalId: this.editForm.get(['animalId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnimalCarrapaticida>>) {
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

  trackAnimalById(index: number, item: IAnimal) {
    return item.id;
  }
}
