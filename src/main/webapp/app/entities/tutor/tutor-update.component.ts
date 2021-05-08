import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ITutor, Tutor } from 'app/shared/model/tutor.model';
import { TutorService } from './tutor.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco/endereco.service';

@Component({
  selector: 'jhi-tutor-update',
  templateUrl: './tutor-update.component.html'
})
export class TutorUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  enderecos: IEndereco[];
  dataCadastroDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [],
    sobrenome: [],
    telefone1: [],
    telefone2: [],
    email: [null, [Validators.required]],
    foto: [],
    fotoContentType: [],
    fotoUrl: [],
    cpf: [],
    sexo: [],
    dataCadastro: [],
    userId: [],
    enderecoId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected tutorService: TutorService,
    protected userService: UserService,
    protected enderecoService: EnderecoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tutor }) => {
      this.updateForm(tutor);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.enderecoService
      .query()
      .subscribe((res: HttpResponse<IEndereco[]>) => (this.enderecos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tutor: ITutor) {
    this.editForm.patchValue({
      id: tutor.id,
      nome: tutor.nome,
      sobrenome: tutor.sobrenome,
      telefone1: tutor.telefone1,
      telefone2: tutor.telefone2,
      email: tutor.email,
      foto: tutor.foto,
      fotoContentType: tutor.fotoContentType,
      fotoUrl: tutor.fotoUrl,
      cpf: tutor.cpf,
      sexo: tutor.sexo,
      dataCadastro: tutor.dataCadastro,
      userId: tutor.userId,
      enderecoId: tutor.enderecoId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tutor = this.createFromForm();
    if (tutor.id !== undefined) {
      this.subscribeToSaveResponse(this.tutorService.update(tutor));
    } else {
      this.subscribeToSaveResponse(this.tutorService.create(tutor));
    }
  }

  private createFromForm(): ITutor {
    return {
      ...new Tutor(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      sobrenome: this.editForm.get(['sobrenome']).value,
      telefone1: this.editForm.get(['telefone1']).value,
      telefone2: this.editForm.get(['telefone2']).value,
      email: this.editForm.get(['email']).value,
      fotoContentType: this.editForm.get(['fotoContentType']).value,
      foto: this.editForm.get(['foto']).value,
      fotoUrl: this.editForm.get(['fotoUrl']).value,
      cpf: this.editForm.get(['cpf']).value,
      sexo: this.editForm.get(['sexo']).value,
      dataCadastro: this.editForm.get(['dataCadastro']).value,
      userId: this.editForm.get(['userId']).value,
      enderecoId: this.editForm.get(['enderecoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITutor>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackEnderecoById(index: number, item: IEndereco) {
    return item.id;
  }
}
