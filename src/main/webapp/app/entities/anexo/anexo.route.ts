import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Anexo } from 'app/shared/model/anexo.model';
import { AnexoService } from './anexo.service';
import { AnexoComponent } from './anexo.component';
import { AnexoDetailComponent } from './anexo-detail.component';
import { AnexoUpdateComponent } from './anexo-update.component';
import { IAnexo } from 'app/shared/model/anexo.model';

@Injectable({ providedIn: 'root' })
export class AnexoResolve implements Resolve<IAnexo> {
  constructor(private service: AnexoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnexo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((anexo: HttpResponse<Anexo>) => anexo.body));
    }
    return of(new Anexo());
  }
}

export const anexoRoute: Routes = [
  {
    path: '',
    component: AnexoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.anexo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnexoDetailComponent,
    resolve: {
      anexo: AnexoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.anexo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnexoUpdateComponent,
    resolve: {
      anexo: AnexoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.anexo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnexoUpdateComponent,
    resolve: {
      anexo: AnexoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.anexo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
