import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleTracking } from 'app/shared/model/vehicle-tracking.model';

@Component({
  selector: 'jhi-vehicle-tracking-detail',
  templateUrl: './vehicle-tracking-detail.component.html'
})
export class VehicleTrackingDetailComponent implements OnInit {
  vehicleTracking: IVehicleTracking | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleTracking }) => (this.vehicleTracking = vehicleTracking));
  }

  previousState(): void {
    window.history.back();
  }
}
