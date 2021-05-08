import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AnexoAtendimento } from 'app/shared/model/anexo-atendimento.model';
import { AnexoAtendimentoService } from './anexo-atendimento.service';
import { AnexoAtendimentoComponent } from './anexo-atendimento.component';
import { AnexoAtendimentoDetailComponent } from './anexo-atendimento-detail.component';
import { AnexoAtendimentoUpdateComponent } from './anexo-atendimento-update.component';
import { IAnexoAtendimento } from 'app/shared/model/anexo-atendimento.model';

@Injectable({ providedIn: 'root' })
export class AnexoAtendimentoResolve implements Resolve<IAnexoAtendimento> {
  constructor(private service: AnexoAtendimentoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnexoAtendimento> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((anexoAtendimento: HttpResponse<AnexoAtendimento>) => anexoAtendimento.body));
    }
    return of(new AnexoAtendimento());
  }
}

export const anexoAtendimentoRoute: Routes = [
  {
    path: '',
    component: AnexoAtendimentoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.anexoAtendimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnexoAtendimentoDetailComponent,
    resolve: {
      anexoAtendimento: AnexoAtendimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.anexoAtendimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnexoAtendimentoUpdateComponent,
    resolve: {
      anexoAtendimento: AnexoAtendimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.anexoAtendimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnexoAtendimentoUpdateComponent,
    resolve: {
      anexoAtendimento: AnexoAtendimentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.anexoAtendimento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
