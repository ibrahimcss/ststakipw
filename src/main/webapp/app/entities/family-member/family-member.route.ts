import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFamilyMember, FamilyMember } from 'app/shared/model/family-member.model';
import { FamilyMemberService } from './family-member.service';
import { FamilyMemberComponent } from './family-member.component';
import { FamilyMemberDetailComponent } from './family-member-detail.component';
import { FamilyMemberUpdateComponent } from './family-member-update.component';

@Injectable({ providedIn: 'root' })
export class FamilyMemberResolve implements Resolve<IFamilyMember> {
  constructor(private service: FamilyMemberService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFamilyMember> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((familyMember: HttpResponse<FamilyMember>) => {
          if (familyMember.body) {
            return of(familyMember.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FamilyMember());
  }
}

export const familyMemberRoute: Routes = [
  {
    path: '',
    component: FamilyMemberComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ststakipApp.familyMember.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FamilyMemberDetailComponent,
    resolve: {
      familyMember: FamilyMemberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.familyMember.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FamilyMemberUpdateComponent,
    resolve: {
      familyMember: FamilyMemberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.familyMember.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FamilyMemberUpdateComponent,
    resolve: {
      familyMember: FamilyMemberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.familyMember.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
