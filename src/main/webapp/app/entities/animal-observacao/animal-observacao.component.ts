import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnimalObservacao } from 'app/shared/model/animal-observacao.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AnimalObservacaoService } from './animal-observacao.service';
import { AnimalObservacaoDeleteDialogComponent } from './animal-observacao-delete-dialog.component';

@Component({
  selector: 'jhi-animal-observacao',
  templateUrl: './animal-observacao.component.html'
})
export class AnimalObservacaoComponent implements OnInit, OnDestroy {
  animalObservacaos: IAnimalObservacao[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    protected animalObservacaoService: AnimalObservacaoService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.animalObservacaoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAnimalObservacao[]>) => this.paginateAnimalObservacaos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/animal-observacao'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/animal-observacao',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInAnimalObservacaos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAnimalObservacao) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInAnimalObservacaos() {
    this.eventSubscriber = this.eventManager.subscribe('animalObservacaoListModification', () => this.loadAll());
  }

  delete(animalObservacao: IAnimalObservacao) {
    const modalRef = this.modalService.open(AnimalObservacaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.animalObservacao = animalObservacao;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAnimalObservacaos(data: IAnimalObservacao[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.animalObservacaos = data;
  }
}
