import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AnimalVermifugo } from 'app/shared/model/animal-vermifugo.model';
import { AnimalVermifugoService } from './animal-vermifugo.service';
import { AnimalVermifugoComponent } from './animal-vermifugo.component';
import { AnimalVermifugoDetailComponent } from './animal-vermifugo-detail.component';
import { AnimalVermifugoUpdateComponent } from './animal-vermifugo-update.component';
import { IAnimalVermifugo } from 'app/shared/model/animal-vermifugo.model';

@Injectable({ providedIn: 'root' })
export class AnimalVermifugoResolve implements Resolve<IAnimalVermifugo> {
  constructor(private service: AnimalVermifugoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnimalVermifugo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((animalVermifugo: HttpResponse<AnimalVermifugo>) => animalVermifugo.body));
    }
    return of(new AnimalVermifugo());
  }
}

export const animalVermifugoRoute: Routes = [
  {
    path: '',
    component: AnimalVermifugoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.animalVermifugo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnimalVermifugoDetailComponent,
    resolve: {
      animalVermifugo: AnimalVermifugoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalVermifugo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnimalVermifugoUpdateComponent,
    resolve: {
      animalVermifugo: AnimalVermifugoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalVermifugo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnimalVermifugoUpdateComponent,
    resolve: {
      animalVermifugo: AnimalVermifugoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalVermifugo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
