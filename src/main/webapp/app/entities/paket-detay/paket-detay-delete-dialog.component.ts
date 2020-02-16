import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaketDetay } from 'app/shared/model/paket-detay.model';
import { PaketDetayService } from './paket-detay.service';

@Component({
  templateUrl: './paket-detay-delete-dialog.component.html'
})
export class PaketDetayDeleteDialogComponent {
  paketDetay?: IPaketDetay;

  constructor(
    protected paketDetayService: PaketDetayService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paketDetayService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paketDetayListModification');
      this.activeModal.close();
    });
  }
}
