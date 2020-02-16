import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFamilyMember } from 'app/shared/model/family-member.model';
import { FamilyMemberService } from './family-member.service';

@Component({
  templateUrl: './family-member-delete-dialog.component.html'
})
export class FamilyMemberDeleteDialogComponent {
  familyMember?: IFamilyMember;

  constructor(
    protected familyMemberService: FamilyMemberService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.familyMemberService.delete(id).subscribe(() => {
      this.eventManager.broadcast('familyMemberListModification');
      this.activeModal.close();
    });
  }
}
