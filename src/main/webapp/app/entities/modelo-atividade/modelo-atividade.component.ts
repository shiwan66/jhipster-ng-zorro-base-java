import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IModeloAtividade } from 'app/shared/model/modelo-atividade.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ModeloAtividadeService } from './modelo-atividade.service';
import { ModeloAtividadeDeleteDialogComponent } from './modelo-atividade-delete-dialog.component';

@Component({
  selector: 'jhi-modelo-atividade',
  templateUrl: './modelo-atividade.component.html'
})
export class ModeloAtividadeComponent implements OnInit, OnDestroy {
  modeloAtividades: IModeloAtividade[];
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
    protected modeloAtividadeService: ModeloAtividadeService,
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
    this.modeloAtividadeService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IModeloAtividade[]>) => this.paginateModeloAtividades(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/modelo-atividade'], {
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
      '/modelo-atividade',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInModeloAtividades();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IModeloAtividade) {
    return item.id;
  }

  registerChangeInModeloAtividades() {
    this.eventSubscriber = this.eventManager.subscribe('modeloAtividadeListModification', () => this.loadAll());
  }

  delete(modeloAtividade: IModeloAtividade) {
    const modalRef = this.modalService.open(ModeloAtividadeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.modeloAtividade = modeloAtividade;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateModeloAtividades(data: IModeloAtividade[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.modeloAtividades = data;
  }
}
