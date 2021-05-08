import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMovimentacaoDeEstoque } from 'app/shared/model/movimentacao-de-estoque.model';
import { MovimentacaoDeEstoqueService } from './movimentacao-de-estoque.service';

@Component({
  templateUrl: './movimentacao-de-estoque-delete-dialog.component.html'
})
export class MovimentacaoDeEstoqueDeleteDialogComponent {
  movimentacaoDeEstoque: IMovimentacaoDeEstoque;

  constructor(
    protected movimentacaoDeEstoqueService: MovimentacaoDeEstoqueService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.movimentacaoDeEstoqueService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'movimentacaoDeEstoqueListModification',
        content: 'Deleted an movimentacaoDeEstoque'
      });
      this.activeModal.dismiss(true);
    });
  }
}
