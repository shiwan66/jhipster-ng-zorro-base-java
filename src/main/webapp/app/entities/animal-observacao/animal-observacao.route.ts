import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AnimalObservacao } from 'app/shared/model/animal-observacao.model';
import { AnimalObservacaoService } from './animal-observacao.service';
import { AnimalObservacaoComponent } from './animal-observacao.component';
import { AnimalObservacaoDetailComponent } from './animal-observacao-detail.component';
import { AnimalObservacaoUpdateComponent } from './animal-observacao-update.component';
import { IAnimalObservacao } from 'app/shared/model/animal-observacao.model';

@Injectable({ providedIn: 'root' })
export class AnimalObservacaoResolve implements Resolve<IAnimalObservacao> {
  constructor(private service: AnimalObservacaoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnimalObservacao> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((animalObservacao: HttpResponse<AnimalObservacao>) => animalObservacao.body));
    }
    return of(new AnimalObservacao());
  }
}

export const animalObservacaoRoute: Routes = [
  {
    path: '',
    component: AnimalObservacaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.animalObservacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnimalObservacaoDetailComponent,
    resolve: {
      animalObservacao: AnimalObservacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalObservacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnimalObservacaoUpdateComponent,
    resolve: {
      animalObservacao: AnimalObservacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalObservacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnimalObservacaoUpdateComponent,
    resolve: {
      animalObservacao: AnimalObservacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalObservacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
