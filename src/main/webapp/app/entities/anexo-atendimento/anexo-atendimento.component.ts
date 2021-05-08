import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnexoAtendimento } from 'app/shared/model/anexo-atendimento.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AnexoAtendimentoService } from './anexo-atendimento.service';
import { AnexoAtendimentoDeleteDialogComponent } from './anexo-atendimento-delete-dialog.component';

@Component({
  selector: 'jhi-anexo-atendimento',
  templateUrl: './anexo-atendimento.component.html'
})
export class AnexoAtendimentoComponent implements OnInit, OnDestroy {
  anexoAtendimentos: IAnexoAtendimento[];
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
    protected anexoAtendimentoService: AnexoAtendimentoService,
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
    this.anexoAtendimentoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAnexoAtendimento[]>) => this.paginateAnexoAtendimentos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/anexo-atendimento'], {
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
      '/anexo-atendimento',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInAnexoAtendimentos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAnexoAtendimento) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInAnexoAtendimentos() {
    this.eventSubscriber = this.eventManager.subscribe('anexoAtendimentoListModification', () => this.loadAll());
  }

  delete(anexoAtendimento: IAnexoAtendimento) {
    const modalRef = this.modalService.open(AnexoAtendimentoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.anexoAtendimento = anexoAtendimento;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAnexoAtendimentos(data: IAnexoAtendimento[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.anexoAtendimentos = data;
  }
}
