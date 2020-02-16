import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StstakipSharedModule } from 'app/shared/shared.module';
import { VehicleTrackingComponent } from './vehicle-tracking.component';
import { VehicleTrackingDetailComponent } from './vehicle-tracking-detail.component';
import { VehicleTrackingUpdateComponent } from './vehicle-tracking-update.component';
import { VehicleTrackingDeleteDialogComponent } from './vehicle-tracking-delete-dialog.component';
import { vehicleTrackingRoute } from './vehicle-tracking.route';

@NgModule({
  imports: [StstakipSharedModule, RouterModule.forChild(vehicleTrackingRoute)],
  declarations: [
    VehicleTrackingComponent,
    VehicleTrackingDetailComponent,
    VehicleTrackingUpdateComponent,
    VehicleTrackingDeleteDialogComponent
  ],
  entryComponents: [VehicleTrackingDeleteDialogComponent]
})
export class StstakipVehicleTrackingModule {}
