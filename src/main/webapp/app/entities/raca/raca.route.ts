import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Raca } from 'app/shared/model/raca.model';
import { RacaService } from './raca.service';
import { RacaComponent } from './raca.component';
import { RacaDetailComponent } from './raca-detail.component';
import { RacaUpdateComponent } from './raca-update.component';
import { IRaca } from 'app/shared/model/raca.model';

@Injectable({ providedIn: 'root' })
export class RacaResolve implements Resolve<IRaca> {
  constructor(private service: RacaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRaca> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((raca: HttpResponse<Raca>) => raca.body));
    }
    return of(new Raca());
  }
}

export const racaRoute: Routes = [
  {
    path: '',
    component: RacaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.raca.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RacaDetailComponent,
    resolve: {
      raca: RacaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.raca.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RacaUpdateComponent,
    resolve: {
      raca: RacaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.raca.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RacaUpdateComponent,
    resolve: {
      raca: RacaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.raca.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
