import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StstakipSharedModule } from 'app/shared/shared.module';
import { FamilyMemberComponent } from './family-member.component';
import { FamilyMemberDetailComponent } from './family-member-detail.component';
import { FamilyMemberUpdateComponent } from './family-member-update.component';
import { FamilyMemberDeleteDialogComponent } from './family-member-delete-dialog.component';
import { familyMemberRoute } from './family-member.route';

@NgModule({
  imports: [StstakipSharedModule, RouterModule.forChild(familyMemberRoute)],
  declarations: [FamilyMemberComponent, FamilyMemberDetailComponent, FamilyMemberUpdateComponent, FamilyMemberDeleteDialogComponent],
  entryComponents: [FamilyMemberDeleteDialogComponent]
})
export class StstakipFamilyMemberModule {}
