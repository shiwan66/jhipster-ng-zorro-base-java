import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MovimentacaoDeEstoque } from 'app/shared/model/movimentacao-de-estoque.model';
import { MovimentacaoDeEstoqueService } from './movimentacao-de-estoque.service';
import { MovimentacaoDeEstoqueComponent } from './movimentacao-de-estoque.component';
import { MovimentacaoDeEstoqueDetailComponent } from './movimentacao-de-estoque-detail.component';
import { MovimentacaoDeEstoqueUpdateComponent } from './movimentacao-de-estoque-update.component';
import { IMovimentacaoDeEstoque } from 'app/shared/model/movimentacao-de-estoque.model';

@Injectable({ providedIn: 'root' })
export class MovimentacaoDeEstoqueResolve implements Resolve<IMovimentacaoDeEstoque> {
  constructor(private service: MovimentacaoDeEstoqueService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMovimentacaoDeEstoque> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((movimentacaoDeEstoque: HttpResponse<MovimentacaoDeEstoque>) => movimentacaoDeEstoque.body));
    }
    return of(new MovimentacaoDeEstoque());
  }
}

export const movimentacaoDeEstoqueRoute: Routes = [
  {
    path: '',
    component: MovimentacaoDeEstoqueComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.movimentacaoDeEstoque.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MovimentacaoDeEstoqueDetailComponent,
    resolve: {
      movimentacaoDeEstoque: MovimentacaoDeEstoqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.movimentacaoDeEstoque.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MovimentacaoDeEstoqueUpdateComponent,
    resolve: {
      movimentacaoDeEstoque: MovimentacaoDeEstoqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.movimentacaoDeEstoque.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MovimentacaoDeEstoqueUpdateComponent,
    resolve: {
      movimentacaoDeEstoque: MovimentacaoDeEstoqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.movimentacaoDeEstoque.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
