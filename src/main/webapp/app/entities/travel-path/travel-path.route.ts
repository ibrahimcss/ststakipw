import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITravelPath, TravelPath } from 'app/shared/model/travel-path.model';
import { TravelPathService } from './travel-path.service';
import { TravelPathComponent } from './travel-path.component';
import { TravelPathDetailComponent } from './travel-path-detail.component';
import { TravelPathUpdateComponent } from './travel-path-update.component';

@Injectable({ providedIn: 'root' })
export class TravelPathResolve implements Resolve<ITravelPath> {
  constructor(private service: TravelPathService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITravelPath> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((travelPath: HttpResponse<TravelPath>) => {
          if (travelPath.body) {
            return of(travelPath.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TravelPath());
  }
}

export const travelPathRoute: Routes = [
  {
    path: '',
    component: TravelPathComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ststakipApp.travelPath.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TravelPathDetailComponent,
    resolve: {
      travelPath: TravelPathResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.travelPath.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TravelPathUpdateComponent,
    resolve: {
      travelPath: TravelPathResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.travelPath.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TravelPathUpdateComponent,
    resolve: {
      travelPath: TravelPathResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.travelPath.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
