import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnimalTipoDeAlteracao } from 'app/shared/model/animal-tipo-de-alteracao.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AnimalTipoDeAlteracaoService } from './animal-tipo-de-alteracao.service';
import { AnimalTipoDeAlteracaoDeleteDialogComponent } from './animal-tipo-de-alteracao-delete-dialog.component';

@Component({
  selector: 'jhi-animal-tipo-de-alteracao',
  templateUrl: './animal-tipo-de-alteracao.component.html'
})
export class AnimalTipoDeAlteracaoComponent implements OnInit, OnDestroy {
  animalTipoDeAlteracaos: IAnimalTipoDeAlteracao[];
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
    protected animalTipoDeAlteracaoService: AnimalTipoDeAlteracaoService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
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
    this.animalTipoDeAlteracaoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAnimalTipoDeAlteracao[]>) => this.paginateAnimalTipoDeAlteracaos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/animal-tipo-de-alteracao'], {
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
      '/animal-tipo-de-alteracao',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInAnimalTipoDeAlteracaos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAnimalTipoDeAlteracao) {
    return item.id;
  }

  registerChangeInAnimalTipoDeAlteracaos() {
    this.eventSubscriber = this.eventManager.subscribe('animalTipoDeAlteracaoListModification', () => this.loadAll());
  }

  delete(animalTipoDeAlteracao: IAnimalTipoDeAlteracao) {
    const modalRef = this.modalService.open(AnimalTipoDeAlteracaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.animalTipoDeAlteracao = animalTipoDeAlteracao;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAnimalTipoDeAlteracaos(data: IAnimalTipoDeAlteracao[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.animalTipoDeAlteracaos = data;
  }
}
