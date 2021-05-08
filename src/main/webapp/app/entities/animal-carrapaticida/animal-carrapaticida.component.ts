import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnimalCarrapaticida } from 'app/shared/model/animal-carrapaticida.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AnimalCarrapaticidaService } from './animal-carrapaticida.service';
import { AnimalCarrapaticidaDeleteDialogComponent } from './animal-carrapaticida-delete-dialog.component';

@Component({
  selector: 'jhi-animal-carrapaticida',
  templateUrl: './animal-carrapaticida.component.html'
})
export class AnimalCarrapaticidaComponent implements OnInit, OnDestroy {
  animalCarrapaticidas: IAnimalCarrapaticida[];
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
    protected animalCarrapaticidaService: AnimalCarrapaticidaService,
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
    this.animalCarrapaticidaService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAnimalCarrapaticida[]>) => this.paginateAnimalCarrapaticidas(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/animal-carrapaticida'], {
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
      '/animal-carrapaticida',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInAnimalCarrapaticidas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAnimalCarrapaticida) {
    return item.id;
  }

  registerChangeInAnimalCarrapaticidas() {
    this.eventSubscriber = this.eventManager.subscribe('animalCarrapaticidaListModification', () => this.loadAll());
  }

  delete(animalCarrapaticida: IAnimalCarrapaticida) {
    const modalRef = this.modalService.open(AnimalCarrapaticidaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.animalCarrapaticida = animalCarrapaticida;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAnimalCarrapaticidas(data: IAnimalCarrapaticida[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.animalCarrapaticidas = data;
  }
}
