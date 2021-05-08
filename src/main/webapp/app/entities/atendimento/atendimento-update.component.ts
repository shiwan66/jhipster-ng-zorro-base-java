import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAtendimento, Atendimento } from 'app/shared/model/atendimento.model';
import { AtendimentoService } from './atendimento.service';
import { IAnimal } from 'app/shared/model/animal.model';
import { AnimalService } from 'app/entities/animal/animal.service';

@Component({
  selector: 'jhi-atendimento-update',
  templateUrl: './atendimento-update.component.html'
})
export class AtendimentoUpdateComponent implements OnInit {
  isSaving: boolean;

  animals: IAnimal[];

  editForm = this.fb.group({
    id: [],
    situacao: [],
    dataDeChegada: [],
    dataDeSaida: [],
    observacao: [],
    animalId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected atendimentoService: AtendimentoService,
    protected animalService: AnimalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ atendimento }) => {
      this.updateForm(atendimento);
    });
    this.animalService
      .query()
      .subscribe((res: HttpResponse<IAnimal[]>) => (this.animals = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(atendimento: IAtendimento) {
    this.editForm.patchValue({
      id: atendimento.id,
      situacao: atendimento.situacao,
      dataDeChegada: atendimento.dataDeChegada != null ? atendimento.dataDeChegada.format(DATE_TIME_FORMAT) : null,
      dataDeSaida: atendimento.dataDeSaida != null ? atendimento.dataDeSaida.format(DATE_TIME_FORMAT) : null,
      observacao: atendimento.observacao,
      animalId: atendimento.animalId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const atendimento = this.createFromForm();
    if (atendimento.id !== undefined) {
      this.subscribeToSaveResponse(this.atendimentoService.update(atendimento));
    } else {
      this.subscribeToSaveResponse(this.atendimentoService.create(atendimento));
    }
  }

  private createFromForm(): IAtendimento {
    return {
      ...new Atendimento(),
      id: this.editForm.get(['id']).value,
      situacao: this.editForm.get(['situacao']).value,
      dataDeChegada:
        this.editForm.get(['dataDeChegada']).value != null
          ? moment(this.editForm.get(['dataDeChegada']).value, DATE_TIME_FORMAT)
          : undefined,
      dataDeSaida:
        this.editForm.get(['dataDeSaida']).value != null ? moment(this.editForm.get(['dataDeSaida']).value, DATE_TIME_FORMAT) : undefined,
      observacao: this.editForm.get(['observacao']).value,
      animalId: this.editForm.get(['animalId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAtendimento>>) {
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
