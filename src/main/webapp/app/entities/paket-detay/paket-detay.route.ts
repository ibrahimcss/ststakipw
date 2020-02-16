import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPaketDetay, PaketDetay } from 'app/shared/model/paket-detay.model';
import { PaketDetayService } from './paket-detay.service';
import { PaketDetayComponent } from './paket-detay.component';
import { PaketDetayDetailComponent } from './paket-detay-detail.component';
import { PaketDetayUpdateComponent } from './paket-detay-update.component';

@Injectable({ providedIn: 'root' })
export class PaketDetayResolve implements Resolve<IPaketDetay> {
  constructor(private service: PaketDetayService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaketDetay> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paketDetay: HttpResponse<PaketDetay>) => {
          if (paketDetay.body) {
            return of(paketDetay.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaketDetay());
  }
}

export const paketDetayRoute: Routes = [
  {
    path: '',
    component: PaketDetayComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ststakipApp.paketDetay.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PaketDetayDetailComponent,
    resolve: {
      paketDetay: PaketDetayResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.paketDetay.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PaketDetayUpdateComponent,
    resolve: {
      paketDetay: PaketDetayResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.paketDetay.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PaketDetayUpdateComponent,
    resolve: {
      paketDetay: PaketDetayResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.paketDetay.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
