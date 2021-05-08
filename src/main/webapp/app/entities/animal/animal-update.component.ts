import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAnimal, Animal } from 'app/shared/model/animal.model';
import { AnimalService } from './animal.service';
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco/endereco.service';
import { IAnimalVeterinario } from 'app/shared/model/animal-veterinario.model';
import { AnimalVeterinarioService } from 'app/entities/animal-veterinario/animal-veterinario.service';
import { IRaca } from 'app/shared/model/raca.model';
import { RacaService } from 'app/entities/raca/raca.service';
import { ITutor } from 'app/shared/model/tutor.model';
import { TutorService } from 'app/entities/tutor/tutor.service';

@Component({
  selector: 'jhi-animal-update',
  templateUrl: './animal-update.component.html'
})
export class AnimalUpdateComponent implements OnInit {
  isSaving: boolean;

  enderecos: IEndereco[];

  animalveterinarios: IAnimalVeterinario[];

  racas: IRaca[];

  tutors: ITutor[];
  dataNascimentoDp: any;

  editForm = this.fb.group({
    id: [],
    foto: [],
    fotoContentType: [],
    fotoUrl: [],
    nome: [],
    sexo: [],
    pelagem: [],
    temperamento: [],
    emAtendimento: [],
    dataNascimento: [],
    enderecoId: [],
    veterinarioId: [],
    racaId: [],
    tutorId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected animalService: AnimalService,
    protected enderecoService: EnderecoService,
    protected animalVeterinarioService: AnimalVeterinarioService,
    protected racaService: RacaService,
    protected tutorService: TutorService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ animal }) => {
      this.updateForm(animal);
    });
    this.enderecoService
      .query()
      .subscribe((res: HttpResponse<IEndereco[]>) => (this.enderecos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.animalVeterinarioService
      .query()
      .subscribe(
        (res: HttpResponse<IAnimalVeterinario[]>) => (this.animalveterinarios = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.racaService
      .query()
      .subscribe((res: HttpResponse<IRaca[]>) => (this.racas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.tutorService
      .query()
      .subscribe((res: HttpResponse<ITutor[]>) => (this.tutors = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(animal: IAnimal) {
    this.editForm.patchValue({
      id: animal.id,
      foto: animal.foto,
      fotoContentType: animal.fotoContentType,
      fotoUrl: animal.fotoUrl,
      nome: animal.nome,
      sexo: animal.sexo,
      pelagem: animal.pelagem,
      temperamento: animal.temperamento,
      emAtendimento: animal.emAtendimento,
      dataNascimento: animal.dataNascimento,
      enderecoId: animal.enderecoId,
      veterinarioId: animal.veterinarioId,
      racaId: animal.racaId,
      tutorId: animal.tutorId
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
    const animal = this.createFromForm();
    if (animal.id !== undefined) {
      this.subscribeToSaveResponse(this.animalService.update(animal));
    } else {
      this.subscribeToSaveResponse(this.animalService.create(animal));
    }
  }

  private createFromForm(): IAnimal {
    return {
      ...new Animal(),
      id: this.editForm.get(['id']).value,
      fotoContentType: this.editForm.get(['fotoContentType']).value,
      foto: this.editForm.get(['foto']).value,
      fotoUrl: this.editForm.get(['fotoUrl']).value,
      nome: this.editForm.get(['nome']).value,
      sexo: this.editForm.get(['sexo']).value,
      pelagem: this.editForm.get(['pelagem']).value,
      temperamento: this.editForm.get(['temperamento']).value,
      emAtendimento: this.editForm.get(['emAtendimento']).value,
      dataNascimento: this.editForm.get(['dataNascimento']).value,
      enderecoId: this.editForm.get(['enderecoId']).value,
      veterinarioId: this.editForm.get(['veterinarioId']).value,
      racaId: this.editForm.get(['racaId']).value,
      tutorId: this.editForm.get(['tutorId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnimal>>) {
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

  trackEnderecoById(index: number, item: IEndereco) {
    return item.id;
  }

  trackAnimalVeterinarioById(index: number, item: IAnimalVeterinario) {
    return item.id;
  }

  trackRacaById(index: number, item: IRaca) {
    return item.id;
  }

  trackTutorById(index: number, item: ITutor) {
    return item.id;
  }
}
