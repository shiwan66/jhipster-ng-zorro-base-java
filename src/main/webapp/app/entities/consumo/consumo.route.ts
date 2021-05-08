import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Consumo } from 'app/shared/model/consumo.model';
import { ConsumoService } from './consumo.service';
import { ConsumoComponent } from './consumo.component';
import { ConsumoDetailComponent } from './consumo-detail.component';
import { ConsumoUpdateComponent } from './consumo-update.component';
import { IConsumo } from 'app/shared/model/consumo.model';

@Injectable({ providedIn: 'root' })
export class ConsumoResolve implements Resolve<IConsumo> {
  constructor(private service: ConsumoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConsumo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((consumo: HttpResponse<Consumo>) => consumo.body));
    }
    return of(new Consumo());
  }
}

export const consumoRoute: Routes = [
  {
    path: '',
    component: ConsumoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.consumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ConsumoDetailComponent,
    resolve: {
      consumo: ConsumoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.consumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ConsumoUpdateComponent,
    resolve: {
      consumo: ConsumoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.consumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ConsumoUpdateComponent,
    resolve: {
      consumo: ConsumoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.consumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
