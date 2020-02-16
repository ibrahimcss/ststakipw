import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVehicleTracking } from 'app/shared/model/vehicle-tracking.model';
import { VehicleTrackingService } from './vehicle-tracking.service';

@Component({
  templateUrl: './vehicle-tracking-delete-dialog.component.html'
})
export class VehicleTrackingDeleteDialogComponent {
  vehicleTracking?: IVehicleTracking;

  constructor(
    protected vehicleTrackingService: VehicleTrackingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehicleTrackingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('vehicleTrackingListModification');
      this.activeModal.close();
    });
  }
}
