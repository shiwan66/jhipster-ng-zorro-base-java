import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IModeloAtividade, ModeloAtividade } from 'app/shared/model/modelo-atividade.model';
import { ModeloAtividadeService } from './modelo-atividade.service';
import { IAtividade } from 'app/shared/model/atividade.model';
import { AtividadeService } from 'app/entities/atividade/atividade.service';

@Component({
  selector: 'jhi-modelo-atividade-update',
  templateUrl: './modelo-atividade-update.component.html'
})
export class ModeloAtividadeUpdateComponent implements OnInit {
  isSaving: boolean;

  atividades: IAtividade[];

  editForm = this.fb.group({
    id: [],
    descricao: [],
    atividades: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected modeloAtividadeService: ModeloAtividadeService,
    protected atividadeService: AtividadeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ modeloAtividade }) => {
      this.updateForm(modeloAtividade);
    });
    this.atividadeService
      .query()
      .subscribe((res: HttpResponse<IAtividade[]>) => (this.atividades = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(modeloAtividade: IModeloAtividade) {
    this.editForm.patchValue({
      id: modeloAtividade.id,
      descricao: modeloAtividade.descricao,
      atividades: modeloAtividade.atividades
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const modeloAtividade = this.createFromForm();
    if (modeloAtividade.id !== undefined) {
      this.subscribeToSaveResponse(this.modeloAtividadeService.update(modeloAtividade));
    } else {
      this.subscribeToSaveResponse(this.modeloAtividadeService.create(modeloAtividade));
    }
  }

  private createFromForm(): IModeloAtividade {
    return {
      ...new ModeloAtividade(),
      id: this.editForm.get(['id']).value,
      descricao: this.editForm.get(['descricao']).value,
      atividades: this.editForm.get(['atividades']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModeloAtividade>>) {
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

  trackAtividadeById(index: number, item: IAtividade) {
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
