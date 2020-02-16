import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StstakipSharedModule } from 'app/shared/shared.module';
import { StudentTrackingComponent } from './student-tracking.component';
import { StudentTrackingDetailComponent } from './student-tracking-detail.component';
import { StudentTrackingUpdateComponent } from './student-tracking-update.component';
import { StudentTrackingDeleteDialogComponent } from './student-tracking-delete-dialog.component';
import { studentTrackingRoute } from './student-tracking.route';

@NgModule({
  imports: [StstakipSharedModule, RouterModule.forChild(studentTrackingRoute)],
  declarations: [
    StudentTrackingComponent,
    StudentTrackingDetailComponent,
    StudentTrackingUpdateComponent,
    StudentTrackingDeleteDialogComponent
  ],
  entryComponents: [StudentTrackingDeleteDialogComponent]
})
export class StstakipStudentTrackingModule {}
