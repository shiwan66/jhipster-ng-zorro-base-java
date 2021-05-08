import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Categoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from './categoria.service';
import { CategoriaComponent } from './categoria.component';
import { CategoriaDetailComponent } from './categoria-detail.component';
import { CategoriaUpdateComponent } from './categoria-update.component';
import { ICategoria } from 'app/shared/model/categoria.model';

@Injectable({ providedIn: 'root' })
export class CategoriaResolve implements Resolve<ICategoria> {
  constructor(private service: CategoriaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoria> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((categoria: HttpResponse<Categoria>) => categoria.body));
    }
    return of(new Categoria());
  }
}

export const categoriaRoute: Routes = [
  {
    path: '',
    component: CategoriaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.categoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategoriaDetailComponent,
    resolve: {
      categoria: CategoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.categoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CategoriaUpdateComponent,
    resolve: {
      categoria: CategoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.categoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CategoriaUpdateComponent,
    resolve: {
      categoria: CategoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.categoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
