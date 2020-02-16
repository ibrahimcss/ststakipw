import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerOf } from 'app/shared/model/customer-of.model';

@Component({
  selector: 'jhi-customer-of-detail',
  templateUrl: './customer-of-detail.component.html'
})
export class CustomerOfDetailComponent implements OnInit {
  customerOf: ICustomerOf | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerOf }) => (this.customerOf = customerOf));
  }

  previousState(): void {
    window.history.back();
  }
}
