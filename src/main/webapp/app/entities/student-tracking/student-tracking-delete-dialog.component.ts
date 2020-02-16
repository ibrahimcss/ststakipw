import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentTracking } from 'app/shared/model/student-tracking.model';
import { StudentTrackingService } from './student-tracking.service';

@Component({
  templateUrl: './student-tracking-delete-dialog.component.html'
})
export class StudentTrackingDeleteDialogComponent {
  studentTracking?: IStudentTracking;

  constructor(
    protected studentTrackingService: StudentTrackingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.studentTrackingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('studentTrackingListModification');
      this.activeModal.close();
    });
  }
}
