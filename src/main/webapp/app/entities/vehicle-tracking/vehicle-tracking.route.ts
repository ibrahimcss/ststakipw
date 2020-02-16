import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVehicleTracking, VehicleTracking } from 'app/shared/model/vehicle-tracking.model';
import { VehicleTrackingService } from './vehicle-tracking.service';
import { VehicleTrackingComponent } from './vehicle-tracking.component';
import { VehicleTrackingDetailComponent } from './vehicle-tracking-detail.component';
import { VehicleTrackingUpdateComponent } from './vehicle-tracking-update.component';

@Injectable({ providedIn: 'root' })
export class VehicleTrackingResolve implements Resolve<IVehicleTracking> {
  constructor(private service: VehicleTrackingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehicleTracking> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((vehicleTracking: HttpResponse<VehicleTracking>) => {
          if (vehicleTracking.body) {
            return of(vehicleTracking.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VehicleTracking());
  }
}

export const vehicleTrackingRoute: Routes = [
  {
    path: '',
    component: VehicleTrackingComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ststakipApp.vehicleTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VehicleTrackingDetailComponent,
    resolve: {
      vehicleTracking: VehicleTrackingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.vehicleTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VehicleTrackingUpdateComponent,
    resolve: {
      vehicleTracking: VehicleTrackingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.vehicleTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VehicleTrackingUpdateComponent,
    resolve: {
      vehicleTracking: VehicleTrackingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ststakipApp.vehicleTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
