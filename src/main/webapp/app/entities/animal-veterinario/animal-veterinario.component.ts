import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnimalVeterinario } from 'app/shared/model/animal-veterinario.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AnimalVeterinarioService } from './animal-veterinario.service';
import { AnimalVeterinarioDeleteDialogComponent } from './animal-veterinario-delete-dialog.component';

@Component({
  selector: 'jhi-animal-veterinario',
  templateUrl: './animal-veterinario.component.html'
})
export class AnimalVeterinarioComponent implements OnInit, OnDestroy {
  animalVeterinarios: IAnimalVeterinario[];
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
    protected animalVeterinarioService: AnimalVeterinarioService,
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
    this.animalVeterinarioService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAnimalVeterinario[]>) => this.paginateAnimalVeterinarios(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/animal-veterinario'], {
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
      '/animal-veterinario',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInAnimalVeterinarios();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAnimalVeterinario) {
    return item.id;
  }

  registerChangeInAnimalVeterinarios() {
    this.eventSubscriber = this.eventManager.subscribe('animalVeterinarioListModification', () => this.loadAll());
  }

  delete(animalVeterinario: IAnimalVeterinario) {
    const modalRef = this.modalService.open(AnimalVeterinarioDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.animalVeterinario = animalVeterinario;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAnimalVeterinarios(data: IAnimalVeterinario[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.animalVeterinarios = data;
  }
}
