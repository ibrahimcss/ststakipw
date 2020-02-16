import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrderPaket, OrderPaket } from 'app/shared/model/order-paket.model';
import { OrderPaketService } from './order-paket.service';
import { OrderPaketComponent } from './order-paket.component';
import { OrderPaketDetailComponent } from './order-paket-detail.component';
import { OrderPaketUpdateComponent } from './order-paket-update.component';

@Injectable({ providedIn: 'root' })
export class OrderPaketResolve implements Resolve<IOrderPaket> {
  constructor(private service: OrderPaketService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderPaket> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((orderPaket: HttpResponse<OrderPaket>) => {
          if (orderPaket.body) {
            return of(orderPaket.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderPaket());
  }
}

export const orderPaketRoute: Routes = [
  {
    path: '',
    component: OrderPaketComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ststakipApp.orderPaket.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrderPaketDetailComponent,
    resolve: {
      orderPaket: OrderPaketResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.orderPaket.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrderPaketUpdateComponent,
    resolve: {
      orderPaket: OrderPaketResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.orderPaket.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrderPaketUpdateComponent,
    resolve: {
      orderPaket: OrderPaketResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.orderPaket.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
