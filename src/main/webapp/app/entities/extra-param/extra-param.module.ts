import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StstakipSharedModule } from 'app/shared/shared.module';
import { ExtraParamComponent } from './extra-param.component';
import { ExtraParamDetailComponent } from './extra-param-detail.component';
import { ExtraParamUpdateComponent } from './extra-param-update.component';
import { ExtraParamDeleteDialogComponent } from './extra-param-delete-dialog.component';
import { extraParamRoute } from './extra-param.route';

@NgModule({
  imports: [StstakipSharedModule, RouterModule.forChild(extraParamRoute)],
  declarations: [ExtraParamComponent, ExtraParamDetailComponent, ExtraParamUpdateComponent, ExtraParamDeleteDialogComponent],
  entryComponents: [ExtraParamDeleteDialogComponent]
})
export class StstakipExtraParamModule {}
