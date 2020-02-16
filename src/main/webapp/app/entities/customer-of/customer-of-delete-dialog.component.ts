import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerOf } from 'app/shared/model/customer-of.model';
import { CustomerOfService } from './customer-of.service';

@Component({
  templateUrl: './customer-of-delete-dialog.component.html'
})
export class CustomerOfDeleteDialogComponent {
  customerOf?: ICustomerOf;

  constructor(
    protected customerOfService: CustomerOfService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customerOfService.delete(id).subscribe(() => {
      this.eventManager.broadcast('customerOfListModification');
      this.activeModal.close();
    });
  }
}
