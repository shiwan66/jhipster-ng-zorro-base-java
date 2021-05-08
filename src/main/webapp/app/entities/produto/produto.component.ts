import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils, JhiAlertService } from 'ng-jhipster';
import { IProduto } from 'app/shared/model/produto.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ProdutoService } from './produto.service';
import { ProdutoDeleteDialogComponent } from './produto-delete-dialog.component';
import { NzModalService } from 'ng-zorro-antd';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'jhi-produto',
  templateUrl: './produto.component.html'
})
export class ProdutoComponent implements OnInit, OnDestroy {
  produtos: IProduto[];
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

  isAllDisplayDataChecked = false;
  isIndeterminate = false;
  idsChecked: { [key: string]: boolean } = {};
  listOfAllData: IProduto[] = [];
  visibleModalExcluir = false;
  visibleFilter = false;

  constructor(
    protected produtoService: ProdutoService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    private alertService: JhiAlertService,
    public modalService: NzModalService,
    private translateService: TranslateService
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
    this.produtoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IProduto[]>) => this.paginateProdutos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/produto'], {
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
      '/produto',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProdutos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProduto) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInProdutos() {
    this.eventSubscriber = this.eventManager.subscribe('produtoListModification', () => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateProdutos(data: IProduto[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.produtos = data;
  }

  openModalDeleteChecked() {
    let aux = false;

    for (const key in this.idsChecked) {
      if (this.idsChecked[key]) {
        aux = true;
      }
    }

    if (!aux) {
      this.alertService.warning('error.notCheckedDelete');
    } else {
      this.visibleModalExcluir = true;
    }
  }

  checkAll(value: boolean): void {
    this.produtos.forEach(item => {
      this.idsChecked[item.id] = value;
    });
  }

  refreshStatus(): void {
    this.isAllDisplayDataChecked = this.produtos.every(item => this.idsChecked[item.id]);
    this.isIndeterminate = this.produtos.some(item => this.idsChecked[item.id]) && !this.isAllDisplayDataChecked;
  }

  traslateString(i18n) {
    return this.translateService.get(i18n);
  }

  async delete(produto: IProduto) {
    const modalRef = this.modalService.create({
      nzTitle: await this.traslateString('entity.delete.title').toPromise(),
      nzContent: ProdutoDeleteDialogComponent,
      nzComponentParams: { produto },
      nzFooter: [
        {
          label: await this.traslateString('entity.action.cancel').toPromise(),
          shape: 'default',
          onClick: () => modalRef.destroy()
        },
        {
          label: await this.traslateString('entity.action.delete').toPromise(),
          type: 'primary',
          onClick: () => {
            this.produtoService.emitEventConfirmDelete();
            modalRef.destroy();
          }
        }
      ]
    });
  }

  deleteAll() {
    const ids = [];

    for (const key in this.idsChecked) {
      if (this.idsChecked[key]) {
        ids.push(key);
      }
    }

    if (ids.length > 0) {
      this.produtoService.deleteMultiple(ids).subscribe(() => {
        this.loadAll();
        this.visibleModalExcluir = false;
      });
    }
  }
}
