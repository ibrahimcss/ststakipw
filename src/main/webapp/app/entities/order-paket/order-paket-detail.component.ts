import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderPaket } from 'app/shared/model/order-paket.model';

@Component({
  selector: 'jhi-order-paket-detail',
  templateUrl: './order-paket-detail.component.html'
})
export class OrderPaketDetailComponent implements OnInit {
  orderPaket: IOrderPaket | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderPaket }) => (this.orderPaket = orderPaket));
  }

  previousState(): void {
    window.history.back();
  }
}
