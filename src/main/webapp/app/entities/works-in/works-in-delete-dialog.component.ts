import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorksIn } from 'app/shared/model/works-in.model';
import { WorksInService } from './works-in.service';

@Component({
  templateUrl: './works-in-delete-dialog.component.html'
})
export class WorksInDeleteDialogComponent {
  worksIn?: IWorksIn;

  constructor(protected worksInService: WorksInService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.worksInService.delete(id).subscribe(() => {
      this.eventManager.broadcast('worksInListModification');
      this.activeModal.close();
    });
  }
}
