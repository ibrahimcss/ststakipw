import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StstakipSharedModule } from 'app/shared/shared.module';
import { PaketDetayComponent } from './paket-detay.component';
import { PaketDetayDetailComponent } from './paket-detay-detail.component';
import { PaketDetayUpdateComponent } from './paket-detay-update.component';
import { PaketDetayDeleteDialogComponent } from './paket-detay-delete-dialog.component';
import { paketDetayRoute } from './paket-detay.route';

@NgModule({
  imports: [StstakipSharedModule, RouterModule.forChild(paketDetayRoute)],
  declarations: [PaketDetayComponent, PaketDetayDetailComponent, PaketDetayUpdateComponent, PaketDetayDeleteDialogComponent],
  entryComponents: [PaketDetayDeleteDialogComponent]
})
export class StstakipPaketDetayModule {}
