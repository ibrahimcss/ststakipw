import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStudentTracking, StudentTracking } from 'app/shared/model/student-tracking.model';
import { StudentTrackingService } from './student-tracking.service';
import { StudentTrackingComponent } from './student-tracking.component';
import { StudentTrackingDetailComponent } from './student-tracking-detail.component';
import { StudentTrackingUpdateComponent } from './student-tracking-update.component';

@Injectable({ providedIn: 'root' })
export class StudentTrackingResolve implements Resolve<IStudentTracking> {
  constructor(private service: StudentTrackingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStudentTracking> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((studentTracking: HttpResponse<StudentTracking>) => {
          if (studentTracking.body) {
            return of(studentTracking.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StudentTracking());
  }
}

export const studentTrackingRoute: Routes = [
  {
    path: '',
    component: StudentTrackingComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ststakipApp.studentTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StudentTrackingDetailComponent,
    resolve: {
      studentTracking: StudentTrackingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.studentTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StudentTrackingUpdateComponent,
    resolve: {
      studentTracking: StudentTrackingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.studentTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StudentTrackingUpdateComponent,
    resolve: {
      studentTracking: StudentTrackingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.studentTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
