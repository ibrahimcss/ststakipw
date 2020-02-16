import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorksIn, WorksIn } from 'app/shared/model/works-in.model';
import { WorksInService } from './works-in.service';
import { WorksInComponent } from './works-in.component';
import { WorksInDetailComponent } from './works-in-detail.component';
import { WorksInUpdateComponent } from './works-in-update.component';

@Injectable({ providedIn: 'root' })
export class WorksInResolve implements Resolve<IWorksIn> {
  constructor(private service: WorksInService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorksIn> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((worksIn: HttpResponse<WorksIn>) => {
          if (worksIn.body) {
            return of(worksIn.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorksIn());
  }
}

export const worksInRoute: Routes = [
  {
    path: '',
    component: WorksInComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ststakipApp.worksIn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WorksInDetailComponent,
    resolve: {
      worksIn: WorksInResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.worksIn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WorksInUpdateComponent,
    resolve: {
      worksIn: WorksInResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.worksIn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WorksInUpdateComponent,
    resolve: {
      worksIn: WorksInResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.worksIn.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
