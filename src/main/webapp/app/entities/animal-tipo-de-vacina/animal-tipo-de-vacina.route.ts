import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AnimalTipoDeVacina } from 'app/shared/model/animal-tipo-de-vacina.model';
import { AnimalTipoDeVacinaService } from './animal-tipo-de-vacina.service';
import { AnimalTipoDeVacinaComponent } from './animal-tipo-de-vacina.component';
import { AnimalTipoDeVacinaDetailComponent } from './animal-tipo-de-vacina-detail.component';
import { AnimalTipoDeVacinaUpdateComponent } from './animal-tipo-de-vacina-update.component';
import { IAnimalTipoDeVacina } from 'app/shared/model/animal-tipo-de-vacina.model';

@Injectable({ providedIn: 'root' })
export class AnimalTipoDeVacinaResolve implements Resolve<IAnimalTipoDeVacina> {
  constructor(private service: AnimalTipoDeVacinaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnimalTipoDeVacina> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((animalTipoDeVacina: HttpResponse<AnimalTipoDeVacina>) => animalTipoDeVacina.body));
    }
    return of(new AnimalTipoDeVacina());
  }
}

export const animalTipoDeVacinaRoute: Routes = [
  {
    path: '',
    component: AnimalTipoDeVacinaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.animalTipoDeVacina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnimalTipoDeVacinaDetailComponent,
    resolve: {
      animalTipoDeVacina: AnimalTipoDeVacinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalTipoDeVacina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnimalTipoDeVacinaUpdateComponent,
    resolve: {
      animalTipoDeVacina: AnimalTipoDeVacinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalTipoDeVacina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnimalTipoDeVacinaUpdateComponent,
    resolve: {
      animalTipoDeVacina: AnimalTipoDeVacinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalTipoDeVacina.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
