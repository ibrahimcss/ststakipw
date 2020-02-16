import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StstakipSharedModule } from 'app/shared/shared.module';
import { StudentToTravelPathComponent } from './student-to-travel-path.component';
import { StudentToTravelPathDetailComponent } from './student-to-travel-path-detail.component';
import { StudentToTravelPathUpdateComponent } from './student-to-travel-path-update.component';
import { StudentToTravelPathDeleteDialogComponent } from './student-to-travel-path-delete-dialog.component';
import { studentToTravelPathRoute } from './student-to-travel-path.route';

@NgModule({
  imports: [StstakipSharedModule, RouterModule.forChild(studentToTravelPathRoute)],
  declarations: [
    StudentToTravelPathComponent,
    StudentToTravelPathDetailComponent,
    StudentToTravelPathUpdateComponent,
    StudentToTravelPathDeleteDialogComponent
  ],
  entryComponents: [StudentToTravelPathDeleteDialogComponent]
})
export class StstakipStudentToTravelPathModule {}
