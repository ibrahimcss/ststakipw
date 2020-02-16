import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStudentToTravelPath, StudentToTravelPath } from 'app/shared/model/student-to-travel-path.model';
import { StudentToTravelPathService } from './student-to-travel-path.service';
import { StudentToTravelPathComponent } from './student-to-travel-path.component';
import { StudentToTravelPathDetailComponent } from './student-to-travel-path-detail.component';
import { StudentToTravelPathUpdateComponent } from './student-to-travel-path-update.component';

@Injectable({ providedIn: 'root' })
export class StudentToTravelPathResolve implements Resolve<IStudentToTravelPath> {
  constructor(private service: StudentToTravelPathService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStudentToTravelPath> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((studentToTravelPath: HttpResponse<StudentToTravelPath>) => {
          if (studentToTravelPath.body) {
            return of(studentToTravelPath.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StudentToTravelPath());
  }
}

export const studentToTravelPathRoute: Routes = [
  {
    path: '',
    component: StudentToTravelPathComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ststakipApp.studentToTravelPath.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StudentToTravelPathDetailComponent,
    resolve: {
      studentToTravelPath: StudentToTravelPathResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.studentToTravelPath.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StudentToTravelPathUpdateComponent,
    resolve: {
      studentToTravelPath: StudentToTravelPathResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.studentToTravelPath.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StudentToTravelPathUpdateComponent,
    resolve: {
      studentToTravelPath: StudentToTravelPathResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.studentToTravelPath.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
