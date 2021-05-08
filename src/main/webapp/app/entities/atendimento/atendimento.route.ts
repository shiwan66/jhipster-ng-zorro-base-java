import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Atendimento } from 'app/shared/model/atendimento.model';
import { AtendimentoService } from './atendimento.service';
import { AtendimentoComponent } from './atendimento.component';
import { AtendimentoDetailComponent } from './atendimento-detail.component';
import { AtendimentoUpdateComponent } from './atendimento-update.component';
import { IAtendimento } from 'app/shared/model/atendimento.model';

@Injectable({ providedIn: 'root' })
export class AtendimentoResolve implements Resolve<IAtendimento> {
  constructor(private service: AtendimentoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAtendimento> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((atendimento: HttpResponse<Atendimento>) => atendimento.body));
    }
    return of(new Atendimento());
  }
}

export const atendimentoRoute: Routes = [
  {
    path: '',
    component: AtendimentoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.atendimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AtendimentoDetailComponent,
    resolve: {
      atendimento: AtendimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.atendimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AtendimentoUpdateComponent,
    resolve: {
      atendimento: AtendimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.atendimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AtendimentoUpdateComponent,
    resolve: {
      atendimento: AtendimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.atendimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
