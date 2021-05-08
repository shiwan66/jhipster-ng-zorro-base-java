import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAnimalVermifugo, AnimalVermifugo } from 'app/shared/model/animal-vermifugo.model';
import { AnimalVermifugoService } from './animal-vermifugo.service';
import { IAnimal } from 'app/shared/model/animal.model';
import { AnimalService } from 'app/entities/animal/animal.service';

@Component({
  selector: 'jhi-animal-vermifugo-update',
  templateUrl: './animal-vermifugo-update.component.html'
})
export class AnimalVermifugoUpdateComponent implements OnInit {
  isSaving: boolean;

  animals: IAnimal[];
  dataDaAplicacaoDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [],
    dataDaAplicacao: [],
    animalId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected animalVermifugoService: AnimalVermifugoService,
    protected animalService: AnimalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ animalVermifugo }) => {
      this.updateForm(animalVermifugo);
    });
    this.animalService
      .query()
      .subscribe((res: HttpResponse<IAnimal[]>) => (this.animals = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(animalVermifugo: IAnimalVermifugo) {
    this.editForm.patchValue({
      id: animalVermifugo.id,
      nome: animalVermifugo.nome,
      dataDaAplicacao: animalVermifugo.dataDaAplicacao,
      animalId: animalVermifugo.animalId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const animalVermifugo = this.createFromForm();
    if (animalVermifugo.id !== undefined) {
      this.subscribeToSaveResponse(this.animalVermifugoService.update(animalVermifugo));
    } else {
      this.subscribeToSaveResponse(this.animalVermifugoService.create(animalVermifugo));
    }
  }

  private createFromForm(): IAnimalVermifugo {
    return {
      ...new AnimalVermifugo(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      dataDaAplicacao: this.editForm.get(['dataDaAplicacao']).value,
      animalId: this.editForm.get(['animalId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnimalVermifugo>>) {
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
