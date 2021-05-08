import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnimalTipoDeVacina } from 'app/shared/model/animal-tipo-de-vacina.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AnimalTipoDeVacinaService } from './animal-tipo-de-vacina.service';
import { AnimalTipoDeVacinaDeleteDialogComponent } from './animal-tipo-de-vacina-delete-dialog.component';

@Component({
  selector: 'jhi-animal-tipo-de-vacina',
  templateUrl: './animal-tipo-de-vacina.component.html'
})
export class AnimalTipoDeVacinaComponent implements OnInit, OnDestroy {
  animalTipoDeVacinas: IAnimalTipoDeVacina[];
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
    protected animalTipoDeVacinaService: AnimalTipoDeVacinaService,
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
    this.animalTipoDeVacinaService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAnimalTipoDeVacina[]>) => this.paginateAnimalTipoDeVacinas(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/animal-tipo-de-vacina'], {
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
      '/animal-tipo-de-vacina',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInAnimalTipoDeVacinas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAnimalTipoDeVacina) {
    return item.id;
  }

  registerChangeInAnimalTipoDeVacinas() {
    this.eventSubscriber = this.eventManager.subscribe('animalTipoDeVacinaListModification', () => this.loadAll());
  }

  delete(animalTipoDeVacina: IAnimalTipoDeVacina) {
    const modalRef = this.modalService.open(AnimalTipoDeVacinaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.animalTipoDeVacina = animalTipoDeVacina;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAnimalTipoDeVacinas(data: IAnimalTipoDeVacina[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.animalTipoDeVacinas = data;
  }
}
