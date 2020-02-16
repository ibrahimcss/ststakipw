import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderPaket } from 'app/shared/model/order-paket.model';
import { OrderPaketService } from './order-paket.service';

@Component({
  templateUrl: './order-paket-delete-dialog.component.html'
})
export class OrderPaketDeleteDialogComponent {
  orderPaket?: IOrderPaket;

  constructor(
    protected orderPaketService: OrderPaketService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderPaketService.delete(id).subscribe(() => {
      this.eventManager.broadcast('orderPaketListModification');
      this.activeModal.close();
    });
  }
}
