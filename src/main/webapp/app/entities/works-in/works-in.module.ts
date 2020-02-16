import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StstakipSharedModule } from 'app/shared/shared.module';
import { WorksInComponent } from './works-in.component';
import { WorksInDetailComponent } from './works-in-detail.component';
import { WorksInUpdateComponent } from './works-in-update.component';
import { WorksInDeleteDialogComponent } from './works-in-delete-dialog.component';
import { worksInRoute } from './works-in.route';

@NgModule({
  imports: [StstakipSharedModule, RouterModule.forChild(worksInRoute)],
  declarations: [WorksInComponent, WorksInDetailComponent, WorksInUpdateComponent, WorksInDeleteDialogComponent],
  entryComponents: [WorksInDeleteDialogComponent]
})
export class StstakipWorksInModule {}
