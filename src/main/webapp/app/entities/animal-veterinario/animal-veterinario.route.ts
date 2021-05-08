import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AnimalVeterinario } from 'app/shared/model/animal-veterinario.model';
import { AnimalVeterinarioService } from './animal-veterinario.service';
import { AnimalVeterinarioComponent } from './animal-veterinario.component';
import { AnimalVeterinarioDetailComponent } from './animal-veterinario-detail.component';
import { AnimalVeterinarioUpdateComponent } from './animal-veterinario-update.component';
import { IAnimalVeterinario } from 'app/shared/model/animal-veterinario.model';

@Injectable({ providedIn: 'root' })
export class AnimalVeterinarioResolve implements Resolve<IAnimalVeterinario> {
  constructor(private service: AnimalVeterinarioService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnimalVeterinario> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((animalVeterinario: HttpResponse<AnimalVeterinario>) => animalVeterinario.body));
    }
    return of(new AnimalVeterinario());
  }
}

export const animalVeterinarioRoute: Routes = [
  {
    path: '',
    component: AnimalVeterinarioComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.animalVeterinario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnimalVeterinarioDetailComponent,
    resolve: {
      animalVeterinario: AnimalVeterinarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalVeterinario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnimalVeterinarioUpdateComponent,
    resolve: {
      animalVeterinario: AnimalVeterinarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalVeterinario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnimalVeterinarioUpdateComponent,
    resolve: {
      animalVeterinario: AnimalVeterinarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.animalVeterinario.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
