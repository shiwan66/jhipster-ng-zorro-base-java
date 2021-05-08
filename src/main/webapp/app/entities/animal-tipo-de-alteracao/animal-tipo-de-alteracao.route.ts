import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AnimalTipoDeAlteracao } from 'app/shared/model/animal-tipo-de-alteracao.model';
import { AnimalTipoDeAlteracaoService } from './animal-tipo-de-alteracao.service';
import { AnimalTipoDeAlteracaoComponent } from './animal-tipo-de-alteracao.component';
import { AnimalTipoDeAlteracaoDetailComponent } from './animal-tipo-de-alteracao-detail.component';
import { AnimalTipoDeAlteracaoUpdateComponent } from './animal-tipo-de-alteracao-update.component';
import { IAnimalTipoDeAlteracao } from 'app/shared/model/animal-tipo-de-alteracao.model';

@Injectable({ providedIn: 'root' })
export class AnimalTipoDeAlteracaoResolve implements Resolve<IAnimalTipoDeAlteracao> {
  constructor(private service: AnimalTipoDeAlteracaoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnimalTipoDeAlteracao> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((animalTipoDeAlteracao: HttpResponse<AnimalTipoDeAlteracao>) => animalTipoDeAlteracao.body));
    }
    return of(new AnimalTipoDeAlteracao());
  }
}

export const animalTipoDeAlteracaoRoute: Routes = [
  {
    path: '',
    component: AnimalTipoDeAlteracaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.animalTipoDeAlteracao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnimalTipoDeAlteracaoDetailComponent,
    resolve: {
      animalTipoDeAlteracao: AnimalTipoDeAlteracaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalTipoDeAlteracao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnimalTipoDeAlteracaoUpdateComponent,
    resolve: {
      animalTipoDeAlteracao: AnimalTipoDeAlteracaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalTipoDeAlteracao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnimalTipoDeAlteracaoUpdateComponent,
    resolve: {
      animalTipoDeAlteracao: AnimalTipoDeAlteracaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalTipoDeAlteracao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
