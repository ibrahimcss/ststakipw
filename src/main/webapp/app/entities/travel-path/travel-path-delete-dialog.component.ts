import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITravelPath } from 'app/shared/model/travel-path.model';
import { TravelPathService } from './travel-path.service';

@Component({
  templateUrl: './travel-path-delete-dialog.component.html'
})
export class TravelPathDeleteDialogComponent {
  travelPath?: ITravelPath;

  constructor(
    protected travelPathService: TravelPathService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.travelPathService.delete(id).subscribe(() => {
      this.eventManager.broadcast('travelPathListModification');
      this.activeModal.close();
    });
  }
}
