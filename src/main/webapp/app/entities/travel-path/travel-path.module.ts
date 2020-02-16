import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StstakipSharedModule } from 'app/shared/shared.module';
import { TravelPathComponent } from './travel-path.component';
import { TravelPathDetailComponent } from './travel-path-detail.component';
import { TravelPathUpdateComponent } from './travel-path-update.component';
import { TravelPathDeleteDialogComponent } from './travel-path-delete-dialog.component';
import { travelPathRoute } from './travel-path.route';

@NgModule({
  imports: [StstakipSharedModule, RouterModule.forChild(travelPathRoute)],
  declarations: [TravelPathComponent, TravelPathDetailComponent, TravelPathUpdateComponent, TravelPathDeleteDialogComponent],
  entryComponents: [TravelPathDeleteDialogComponent]
})
export class StstakipTravelPathModule {}
