import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StstakipSharedModule } from 'app/shared/shared.module';
import { OrderPaketComponent } from './order-paket.component';
import { OrderPaketDetailComponent } from './order-paket-detail.component';
import { OrderPaketUpdateComponent } from './order-paket-update.component';
import { OrderPaketDeleteDialogComponent } from './order-paket-delete-dialog.component';
import { orderPaketRoute } from './order-paket.route';

@NgModule({
  imports: [StstakipSharedModule, RouterModule.forChild(orderPaketRoute)],
  declarations: [OrderPaketComponent, OrderPaketDetailComponent, OrderPaketUpdateComponent, OrderPaketDeleteDialogComponent],
  entryComponents: [OrderPaketDeleteDialogComponent]
})
export class StstakipOrderPaketModule {}
