import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Titulo } from 'app/shared/model/titulo.model';
import { TituloService } from './titulo.service';
import { TituloComponent } from './titulo.component';
import { TituloDetailComponent } from './titulo-detail.component';
import { TituloUpdateComponent } from './titulo-update.component';
import { ITitulo } from 'app/shared/model/titulo.model';

@Injectable({ providedIn: 'root' })
export class TituloResolve implements Resolve<ITitulo> {
  constructor(private service: TituloService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITitulo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((titulo: HttpResponse<Titulo>) => titulo.body));
    }
    return of(new Titulo());
  }
}

export const tituloRoute: Routes = [
  {
    path: '',
    component: TituloComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.titulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TituloDetailComponent,
    resolve: {
      titulo: TituloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.titulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TituloUpdateComponent,
    resolve: {
      titulo: TituloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.titulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TituloUpdateComponent,
    resolve: {
      titulo: TituloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.titulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
