import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StstakipSharedModule } from 'app/shared/shared.module';
import { CustomerOfComponent } from './customer-of.component';
import { CustomerOfDetailComponent } from './customer-of-detail.component';
import { CustomerOfUpdateComponent } from './customer-of-update.component';
import { CustomerOfDeleteDialogComponent } from './customer-of-delete-dialog.component';
import { customerOfRoute } from './customer-of.route';

@NgModule({
  imports: [StstakipSharedModule, RouterModule.forChild(customerOfRoute)],
  declarations: [CustomerOfComponent, CustomerOfDetailComponent, CustomerOfUpdateComponent, CustomerOfDeleteDialogComponent],
  entryComponents: [CustomerOfDeleteDialogComponent]
})
export class StstakipCustomerOfModule {}
