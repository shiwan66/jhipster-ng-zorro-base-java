import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnimalAlteracao } from 'app/shared/model/animal-alteracao.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AnimalAlteracaoService } from './animal-alteracao.service';
import { AnimalAlteracaoDeleteDialogComponent } from './animal-alteracao-delete-dialog.component';

@Component({
  selector: 'jhi-animal-alteracao',
  templateUrl: './animal-alteracao.component.html'
})
export class AnimalAlteracaoComponent implements OnInit, OnDestroy {
  animalAlteracaos: IAnimalAlteracao[];
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
    protected animalAlteracaoService: AnimalAlteracaoService,
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
    this.animalAlteracaoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAnimalAlteracao[]>) => this.paginateAnimalAlteracaos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/animal-alteracao'], {
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
      '/animal-alteracao',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInAnimalAlteracaos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAnimalAlteracao) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInAnimalAlteracaos() {
    this.eventSubscriber = this.eventManager.subscribe('animalAlteracaoListModification', () => this.loadAll());
  }

  delete(animalAlteracao: IAnimalAlteracao) {
    const modalRef = this.modalService.open(AnimalAlteracaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.animalAlteracao = animalAlteracao;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAnimalAlteracaos(data: IAnimalAlteracao[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.animalAlteracaos = data;
  }
}
