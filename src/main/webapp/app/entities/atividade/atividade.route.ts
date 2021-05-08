import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Atividade } from 'app/shared/model/atividade.model';
import { AtividadeService } from './atividade.service';
import { AtividadeComponent } from './atividade.component';
import { AtividadeDetailComponent } from './atividade-detail.component';
import { AtividadeUpdateComponent } from './atividade-update.component';
import { IAtividade } from 'app/shared/model/atividade.model';

@Injectable({ providedIn: 'root' })
export class AtividadeResolve implements Resolve<IAtividade> {
  constructor(private service: AtividadeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAtividade> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((atividade: HttpResponse<Atividade>) => atividade.body));
    }
    return of(new Atividade());
  }
}

export const atividadeRoute: Routes = [
  {
    path: '',
    component: AtividadeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.atividade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AtividadeDetailComponent,
    resolve: {
      atividade: AtividadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.atividade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AtividadeUpdateComponent,
    resolve: {
      atividade: AtividadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.atividade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AtividadeUpdateComponent,
    resolve: {
      atividade: AtividadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.atividade.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
