import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRaca, Raca } from 'app/shared/model/raca.model';
import { RacaService } from './raca.service';

@Component({
  selector: 'jhi-raca-update',
  templateUrl: './raca-update.component.html'
})
export class RacaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: []
  });

  constructor(protected racaService: RacaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ raca }) => {
      this.updateForm(raca);
    });
  }

  updateForm(raca: IRaca) {
    this.editForm.patchValue({
      id: raca.id,
      nome: raca.nome
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const raca = this.createFromForm();
    if (raca.id !== undefined) {
      this.subscribeToSaveResponse(this.racaService.update(raca));
    } else {
      this.subscribeToSaveResponse(this.racaService.create(raca));
    }
  }

  private createFromForm(): IRaca {
    return {
      ...new Raca(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRaca>>) {
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
