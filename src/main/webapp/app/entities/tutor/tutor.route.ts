import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Tutor } from 'app/shared/model/tutor.model';
import { TutorService } from './tutor.service';
import { TutorComponent } from './tutor.component';
import { TutorDetailComponent } from './tutor-detail.component';
import { TutorUpdateComponent } from './tutor-update.component';
import { ITutor } from 'app/shared/model/tutor.model';

@Injectable({ providedIn: 'root' })
export class TutorResolve implements Resolve<ITutor> {
  constructor(private service: TutorService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITutor> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((tutor: HttpResponse<Tutor>) => tutor.body));
    }
    return of(new Tutor());
  }
}

export const tutorRoute: Routes = [
  {
    path: '',
    component: TutorComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ngzorroApp.tutor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TutorDetailComponent,
    resolve: {
      tutor: TutorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.tutor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TutorUpdateComponent,
    resolve: {
      tutor: TutorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.tutor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TutorUpdateComponent,
    resolve: {
      tutor: TutorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ngzorroApp.tutor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
