import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AnimalVacina } from 'app/shared/model/animal-vacina.model';
import { AnimalVacinaService } from './animal-vacina.service';
import { AnimalVacinaComponent } from './animal-vacina.component';
import { AnimalVacinaDetailComponent } from './animal-vacina-detail.component';
import { AnimalVacinaUpdateComponent } from './animal-vacina-update.component';
import { IAnimalVacina } from 'app/shared/model/animal-vacina.model';

@Injectable({ providedIn: 'root' })
export class AnimalVacinaResolve implements Resolve<IAnimalVacina> {
  constructor(private service: AnimalVacinaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnimalVacina> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((animalVacina: HttpResponse<AnimalVacina>) => animalVacina.body));
    }
    return of(new AnimalVacina());
  }
}

export const animalVacinaRoute: Routes = [
  {
    path: '',
    component: AnimalVacinaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.animalVacina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnimalVacinaDetailComponent,
    resolve: {
      animalVacina: AnimalVacinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalVacina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnimalVacinaUpdateComponent,
    resolve: {
      animalVacina: AnimalVacinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalVacina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnimalVacinaUpdateComponent,
    resolve: {
      animalVacina: AnimalVacinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalVacina.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
