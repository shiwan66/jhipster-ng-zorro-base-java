import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AnimalCarrapaticida } from 'app/shared/model/animal-carrapaticida.model';
import { AnimalCarrapaticidaService } from './animal-carrapaticida.service';
import { AnimalCarrapaticidaComponent } from './animal-carrapaticida.component';
import { AnimalCarrapaticidaDetailComponent } from './animal-carrapaticida-detail.component';
import { AnimalCarrapaticidaUpdateComponent } from './animal-carrapaticida-update.component';
import { IAnimalCarrapaticida } from 'app/shared/model/animal-carrapaticida.model';

@Injectable({ providedIn: 'root' })
export class AnimalCarrapaticidaResolve implements Resolve<IAnimalCarrapaticida> {
  constructor(private service: AnimalCarrapaticidaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnimalCarrapaticida> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((animalCarrapaticida: HttpResponse<AnimalCarrapaticida>) => animalCarrapaticida.body));
    }
    return of(new AnimalCarrapaticida());
  }
}

export const animalCarrapaticidaRoute: Routes = [
  {
    path: '',
    component: AnimalCarrapaticidaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.animalCarrapaticida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnimalCarrapaticidaDetailComponent,
    resolve: {
      animalCarrapaticida: AnimalCarrapaticidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalCarrapaticida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnimalCarrapaticidaUpdateComponent,
    resolve: {
      animalCarrapaticida: AnimalCarrapaticidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalCarrapaticida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnimalCarrapaticidaUpdateComponent,
    resolve: {
      animalCarrapaticida: AnimalCarrapaticidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalCarrapaticida.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
