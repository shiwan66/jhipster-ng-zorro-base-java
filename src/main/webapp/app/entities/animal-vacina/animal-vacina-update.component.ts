import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAnimalVacina, AnimalVacina } from 'app/shared/model/animal-vacina.model';
import { AnimalVacinaService } from './animal-vacina.service';
import { IAnimalTipoDeVacina } from 'app/shared/model/animal-tipo-de-vacina.model';
import { AnimalTipoDeVacinaService } from 'app/entities/animal-tipo-de-vacina/animal-tipo-de-vacina.service';
import { IAnimal } from 'app/shared/model/animal.model';
import { AnimalService } from 'app/entities/animal/animal.service';

@Component({
  selector: 'jhi-animal-vacina-update',
  templateUrl: './animal-vacina-update.component.html'
})
export class AnimalVacinaUpdateComponent implements OnInit {
  isSaving: boolean;

  animaltipodevacinas: IAnimalTipoDeVacina[];

  animals: IAnimal[];
  dataDaAplicacaoDp: any;

  editForm = this.fb.group({
    id: [],
    dataDaAplicacao: [],
    animalTipoDeVacinaId: [],
    animalId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected animalVacinaService: AnimalVacinaService,
    protected animalTipoDeVacinaService: AnimalTipoDeVacinaService,
    protected animalService: AnimalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ animalVacina }) => {
      this.updateForm(animalVacina);
    });
    this.animalTipoDeVacinaService
      .query()
      .subscribe(
        (res: HttpResponse<IAnimalTipoDeVacina[]>) => (this.animaltipodevacinas = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.animalService
      .query()
      .subscribe((res: HttpResponse<IAnimal[]>) => (this.animals = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(animalVacina: IAnimalVacina) {
    this.editForm.patchValue({
      id: animalVacina.id,
      dataDaAplicacao: animalVacina.dataDaAplicacao,
      animalTipoDeVacinaId: animalVacina.animalTipoDeVacinaId,
      animalId: animalVacina.animalId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const animalVacina = this.createFromForm();
    if (animalVacina.id !== undefined) {
      this.subscribeToSaveResponse(this.animalVacinaService.update(animalVacina));
    } else {
      this.subscribeToSaveResponse(this.animalVacinaService.create(animalVacina));
    }
  }

  private createFromForm(): IAnimalVacina {
    return {
      ...new AnimalVacina(),
      id: this.editForm.get(['id']).value,
      dataDaAplicacao: this.editForm.get(['dataDaAplicacao']).value,
      animalTipoDeVacinaId: this.editForm.get(['animalTipoDeVacinaId']).value,
      animalId: this.editForm.get(['animalId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnimalVacina>>) {
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

  trackAnimalTipoDeVacinaById(index: number, item: IAnimalTipoDeVacina) {
    return item.id;
  }

  trackAnimalById(index: number, item: IAnimal) {
    return item.id;
  }
}
