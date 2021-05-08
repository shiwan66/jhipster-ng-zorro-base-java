import { Component, Input, OnDestroy } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';

import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from './produto.service';

@Component({
  templateUrl: './produto-delete-dialog.component.html'
})
export class ProdutoDeleteDialogComponent implements OnDestroy {
  @Input() produto: IProduto;
  eventSubscribe: any;
  constructor(protected produtoService: ProdutoService, protected eventManager: JhiEventManager) {
    this.eventSubscribe = this.produtoService.eventConfirmDelete.subscribe(() => this.confirmDelete());
  }

  confirmDelete() {
    this.produtoService.delete(this.produto.id).subscribe(() => {
      this.eventManager.broadcast({ name: 'produtoListModification', content: 'Deleted a produto' });
    });
  }

  ngOnDestroy(): void {
    this.eventSubscribe.unsubscribe();
  }
}
