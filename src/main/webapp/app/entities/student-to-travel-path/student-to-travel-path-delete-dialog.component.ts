import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentToTravelPath } from 'app/shared/model/student-to-travel-path.model';
import { StudentToTravelPathService } from './student-to-travel-path.service';

@Component({
  templateUrl: './student-to-travel-path-delete-dialog.component.html'
})
export class StudentToTravelPathDeleteDialogComponent {
  studentToTravelPath?: IStudentToTravelPath;

  constructor(
    protected studentToTravelPathService: StudentToTravelPathService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.studentToTravelPathService.delete(id).subscribe(() => {
      this.eventManager.broadcast('studentToTravelPathListModification');
      this.activeModal.close();
    });
  }
}
