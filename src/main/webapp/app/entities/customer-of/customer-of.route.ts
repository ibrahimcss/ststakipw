import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICustomerOf, CustomerOf } from 'app/shared/model/customer-of.model';
import { CustomerOfService } from './customer-of.service';
import { CustomerOfComponent } from './customer-of.component';
import { CustomerOfDetailComponent } from './customer-of-detail.component';
import { CustomerOfUpdateComponent } from './customer-of-update.component';

@Injectable({ providedIn: 'root' })
export class CustomerOfResolve implements Resolve<ICustomerOf> {
  constructor(private service: CustomerOfService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerOf> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((customerOf: HttpResponse<CustomerOf>) => {
          if (customerOf.body) {
            return of(customerOf.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomerOf());
  }
}

export const customerOfRoute: Routes = [
  {
    path: '',
    component: CustomerOfComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ststakipApp.customerOf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CustomerOfDetailComponent,
    resolve: {
      customerOf: CustomerOfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.customerOf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CustomerOfUpdateComponent,
    resolve: {
      customerOf: CustomerOfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.customerOf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CustomerOfUpdateComponent,
    resolve: {
      customerOf: CustomerOfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.customerOf.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
