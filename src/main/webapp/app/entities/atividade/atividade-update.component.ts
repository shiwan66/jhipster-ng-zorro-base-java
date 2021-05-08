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
import { IAtividade, Atividade } from 'app/shared/model/atividade.model';
import { AtividadeService } from './atividade.service';
import { IAtendimento } from 'app/shared/model/atendimento.model';
import { AtendimentoService } from 'app/entities/atendimento/atendimento.service';
import { IModeloAtividade } from 'app/shared/model/modelo-atividade.model';
import { ModeloAtividadeService } from 'app/entities/modelo-atividade/modelo-atividade.service';

@Component({
  selector: 'jhi-atividade-update',
  templateUrl: './atividade-update.component.html'
})
export class AtividadeUpdateComponent implements OnInit {
  isSaving: boolean;

  atendimentos: IAtendimento[];

  modeloatividades: IModeloAtividade[];

  editForm = this.fb.group({
    id: [],
    titulo: [],
    inicio: [],
    termino: [],
    observacao: [],
    realizado: [],
    atendimentoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected atividadeService: AtividadeService,
    protected atendimentoService: AtendimentoService,
    protected modeloAtividadeService: ModeloAtividadeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ atividade }) => {
      this.updateForm(atividade);
    });
    this.atendimentoService
      .query()
      .subscribe(
        (res: HttpResponse<IAtendimento[]>) => (this.atendimentos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.modeloAtividadeService
      .query()
      .subscribe(
        (res: HttpResponse<IModeloAtividade[]>) => (this.modeloatividades = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(atividade: IAtividade) {
    this.editForm.patchValue({
      id: atividade.id,
      titulo: atividade.titulo,
      inicio: atividade.inicio != null ? atividade.inicio.format(DATE_TIME_FORMAT) : null,
      termino: atividade.termino != null ? atividade.termino.format(DATE_TIME_FORMAT) : null,
      observacao: atividade.observacao,
      realizado: atividade.realizado,
      atendimentoId: atividade.atendimentoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const atividade = this.createFromForm();
    if (atividade.id !== undefined) {
      this.subscribeToSaveResponse(this.atividadeService.update(atividade));
    } else {
      this.subscribeToSaveResponse(this.atividadeService.create(atividade));
    }
  }

  private createFromForm(): IAtividade {
    return {
      ...new Atividade(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      inicio: this.editForm.get(['inicio']).value != null ? moment(this.editForm.get(['inicio']).value, DATE_TIME_FORMAT) : undefined,
      termino: this.editForm.get(['termino']).value != null ? moment(this.editForm.get(['termino']).value, DATE_TIME_FORMAT) : undefined,
      observacao: this.editForm.get(['observacao']).value,
      realizado: this.editForm.get(['realizado']).value,
      atendimentoId: this.editForm.get(['atendimentoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAtividade>>) {
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

  trackAtendimentoById(index: number, item: IAtendimento) {
    return item.id;
  }

  trackModeloAtividadeById(index: number, item: IModeloAtividade) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
