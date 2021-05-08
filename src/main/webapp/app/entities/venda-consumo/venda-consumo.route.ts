import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { VendaConsumo } from 'app/shared/model/venda-consumo.model';
import { VendaConsumoService } from './venda-consumo.service';
import { VendaConsumoComponent } from './venda-consumo.component';
import { VendaConsumoDetailComponent } from './venda-consumo-detail.component';
import { VendaConsumoUpdateComponent } from './venda-consumo-update.component';
import { IVendaConsumo } from 'app/shared/model/venda-consumo.model';

@Injectable({ providedIn: 'root' })
export class VendaConsumoResolve implements Resolve<IVendaConsumo> {
  constructor(private service: VendaConsumoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVendaConsumo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((vendaConsumo: HttpResponse<VendaConsumo>) => vendaConsumo.body));
    }
    return of(new VendaConsumo());
  }
}

export const vendaConsumoRoute: Routes = [
  {
    path: '',
    component: VendaConsumoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.vendaConsumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VendaConsumoDetailComponent,
    resolve: {
      vendaConsumo: VendaConsumoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.vendaConsumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VendaConsumoUpdateComponent,
    resolve: {
      vendaConsumo: VendaConsumoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.vendaConsumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VendaConsumoUpdateComponent,
    resolve: {
      vendaConsumo: VendaConsumoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.vendaConsumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
