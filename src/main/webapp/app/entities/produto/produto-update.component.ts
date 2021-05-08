import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProduto, Produto } from 'app/shared/model/produto.model';
import { ProdutoService } from './produto.service';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria/categoria.service';

@Component({
  selector: 'jhi-produto-update',
  templateUrl: './produto-update.component.html'
})
export class ProdutoUpdateComponent implements OnInit {
  isSaving: boolean;

  categorias: ICategoria[];
  dataDp: any;

  editForm = this.fb.group({
    id: [],
    imagem: [],
    imagemContentType: [],
    nome: [null, [Validators.required]],
    descricao: [],
    preco: [],
    data: [],
    hora: [],
    categorias: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected produtoService: ProdutoService,
    protected categoriaService: CategoriaService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ produto }) => {
      this.updateForm(produto);
    });
    this.categoriaService
      .query()
      .subscribe((res: HttpResponse<ICategoria[]>) => (this.categorias = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(produto: IProduto) {
    this.editForm.patchValue({
      id: produto.id,
      imagem: produto.imagem,
      imagemContentType: produto.imagemContentType,
      nome: produto.nome,
      descricao: produto.descricao,
      preco: produto.preco,
      data: produto.data,
      hora: produto.hora != null ? produto.hora.format(DATE_TIME_FORMAT) : null,
      categorias: produto.categorias
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  customReq(item) {
    return Subscription.EMPTY;
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.file) {
        const file: File = event.file.originFileObj;
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
    const produto = this.createFromForm();

    if (produto.id !== undefined) {
      this.subscribeToSaveResponse(this.produtoService.update(produto));
    } else {
      this.subscribeToSaveResponse(this.produtoService.create(produto));
    }
  }

  private createFromForm(): IProduto {
    return {
      ...new Produto(),
      id: this.editForm.get(['id']).value,
      imagemContentType: this.editForm.get(['imagemContentType']).value,
      imagem: this.editForm.get(['imagem']).value,
      nome: this.editForm.get(['nome']).value,
      descricao: this.editForm.get(['descricao']).value,
      preco: this.editForm.get(['preco']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value) : null,
      hora: this.editForm.get(['hora']).value != null ? moment(this.editForm.get(['hora']).value, DATE_TIME_FORMAT) : undefined,
      categorias: this.editForm.get(['categorias']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduto>>) {
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

  trackCategoriaById(index: number, item: ICategoria) {
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
