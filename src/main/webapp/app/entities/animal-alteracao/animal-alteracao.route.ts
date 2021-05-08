import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AnimalAlteracao } from 'app/shared/model/animal-alteracao.model';
import { AnimalAlteracaoService } from './animal-alteracao.service';
import { AnimalAlteracaoComponent } from './animal-alteracao.component';
import { AnimalAlteracaoDetailComponent } from './animal-alteracao-detail.component';
import { AnimalAlteracaoUpdateComponent } from './animal-alteracao-update.component';
import { IAnimalAlteracao } from 'app/shared/model/animal-alteracao.model';

@Injectable({ providedIn: 'root' })
export class AnimalAlteracaoResolve implements Resolve<IAnimalAlteracao> {
  constructor(private service: AnimalAlteracaoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnimalAlteracao> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((animalAlteracao: HttpResponse<AnimalAlteracao>) => animalAlteracao.body));
    }
    return of(new AnimalAlteracao());
  }
}

export const animalAlteracaoRoute: Routes = [
  {
    path: '',
    component: AnimalAlteracaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.animalAlteracao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnimalAlteracaoDetailComponent,
    resolve: {
      animalAlteracao: AnimalAlteracaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalAlteracao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnimalAlteracaoUpdateComponent,
    resolve: {
      animalAlteracao: AnimalAlteracaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalAlteracao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnimalAlteracaoUpdateComponent,
    resolve: {
      animalAlteracao: AnimalAlteracaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalAlteracao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
