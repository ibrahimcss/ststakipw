import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExtraParam } from 'app/shared/model/extra-param.model';
import { ExtraParamService } from './extra-param.service';

@Component({
  templateUrl: './extra-param-delete-dialog.component.html'
})
export class ExtraParamDeleteDialogComponent {
  extraParam?: IExtraParam;

  constructor(
    protected extraParamService: ExtraParamService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.extraParamService.delete(id).subscribe(() => {
      this.eventManager.broadcast('extraParamListModification');
      this.activeModal.close();
    });
  }
}
