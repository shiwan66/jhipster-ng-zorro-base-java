import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITitulo } from 'app/shared/model/titulo.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TituloService } from './titulo.service';
import { TituloDeleteDialogComponent } from './titulo-delete-dialog.component';

@Component({
  selector: 'jhi-titulo',
  templateUrl: './titulo.component.html'
})
export class TituloComponent implements OnInit, OnDestroy {
  titulos: ITitulo[];
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
    protected tituloService: TituloService,
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
    this.tituloService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITitulo[]>) => this.paginateTitulos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/titulo'], {
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
      '/titulo',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInTitulos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITitulo) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInTitulos() {
    this.eventSubscriber = this.eventManager.subscribe('tituloListModification', () => this.loadAll());
  }

  delete(titulo: ITitulo) {
    const modalRef = this.modalService.open(TituloDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.titulo = titulo;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTitulos(data: ITitulo[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.titulos = data;
  }
}
