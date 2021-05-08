import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AnimalCio } from 'app/shared/model/animal-cio.model';
import { AnimalCioService } from './animal-cio.service';
import { AnimalCioComponent } from './animal-cio.component';
import { AnimalCioDetailComponent } from './animal-cio-detail.component';
import { AnimalCioUpdateComponent } from './animal-cio-update.component';
import { IAnimalCio } from 'app/shared/model/animal-cio.model';

@Injectable({ providedIn: 'root' })
export class AnimalCioResolve implements Resolve<IAnimalCio> {
  constructor(private service: AnimalCioService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnimalCio> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((animalCio: HttpResponse<AnimalCio>) => animalCio.body));
    }
    return of(new AnimalCio());
  }
}

export const animalCioRoute: Routes = [
  {
    path: '',
    component: AnimalCioComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.animalCio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnimalCioDetailComponent,
    resolve: {
      animalCio: AnimalCioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalCio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnimalCioUpdateComponent,
    resolve: {
      animalCio: AnimalCioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalCio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnimalCioUpdateComponent,
    resolve: {
      animalCio: AnimalCioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalCio.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
