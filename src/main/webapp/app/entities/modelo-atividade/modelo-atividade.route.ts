import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ModeloAtividade } from 'app/shared/model/modelo-atividade.model';
import { ModeloAtividadeService } from './modelo-atividade.service';
import { ModeloAtividadeComponent } from './modelo-atividade.component';
import { ModeloAtividadeDetailComponent } from './modelo-atividade-detail.component';
import { ModeloAtividadeUpdateComponent } from './modelo-atividade-update.component';
import { IModeloAtividade } from 'app/shared/model/modelo-atividade.model';

@Injectable({ providedIn: 'root' })
export class ModeloAtividadeResolve implements Resolve<IModeloAtividade> {
  constructor(private service: ModeloAtividadeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IModeloAtividade> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((modeloAtividade: HttpResponse<ModeloAtividade>) => modeloAtividade.body));
    }
    return of(new ModeloAtividade());
  }
}

export const modeloAtividadeRoute: Routes = [
  {
    path: '',
    component: ModeloAtividadeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.modeloAtividade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ModeloAtividadeDetailComponent,
    resolve: {
      modeloAtividade: ModeloAtividadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.modeloAtividade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ModeloAtividadeUpdateComponent,
    resolve: {
      modeloAtividade: ModeloAtividadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.modeloAtividade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ModeloAtividadeUpdateComponent,
    resolve: {
      modeloAtividade: ModeloAtividadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.modeloAtividade.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
