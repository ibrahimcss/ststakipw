import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrderPaket, OrderPaket } from 'app/shared/model/order-paket.model';
import { OrderPaketService } from './order-paket.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';
import { IPaketDetay } from 'app/shared/model/paket-detay.model';
import { PaketDetayService } from 'app/entities/paket-detay/paket-detay.service';

type SelectableEntity = ICompany | IPaketDetay;

@Component({
  selector: 'jhi-order-paket-update',
  templateUrl: './order-paket-update.component.html'
})
export class OrderPaketUpdateComponent implements OnInit {
  isSaving = false;
  companies: ICompany[] = [];
  paketdetays: IPaketDetay[] = [];

  editForm = this.fb.group({
    id: [],
    orderedDate: [null, [Validators.required]],
    isExpired: [],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    companyId: [null, Validators.required],
    paketDetayId: [null, Validators.required]
  });

  constructor(
    protected orderPaketService: OrderPaketService,
    protected companyService: CompanyService,
    protected paketDetayService: PaketDetayService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderPaket }) => {
      if (!orderPaket.id) {
        const today = moment().startOf('day');
        orderPaket.orderedDate = today;
        orderPaket.startDate = today;
        orderPaket.endDate = today;
      }

      this.updateForm(orderPaket);

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));

      this.paketDetayService.query().subscribe((res: HttpResponse<IPaketDetay[]>) => (this.paketdetays = res.body || []));
    });
  }

  updateForm(orderPaket: IOrderPaket): void {
    this.editForm.patchValue({
      id: orderPaket.id,
      orderedDate: orderPaket.orderedDate ? orderPaket.orderedDate.format(DATE_TIME_FORMAT) : null,
      isExpired: orderPaket.isExpired,
      startDate: orderPaket.startDate ? orderPaket.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: orderPaket.endDate ? orderPaket.endDate.format(DATE_TIME_FORMAT) : null,
      companyId: orderPaket.companyId,
      paketDetayId: orderPaket.paketDetayId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderPaket = this.createFromForm();
    if (orderPaket.id !== undefined) {
      this.subscribeToSaveResponse(this.orderPaketService.update(orderPaket));
    } else {
      this.subscribeToSaveResponse(this.orderPaketService.create(orderPaket));
    }
  }

  private createFromForm(): IOrderPaket {
    return {
      ...new OrderPaket(),
      id: this.editForm.get(['id'])!.value,
      orderedDate: this.editForm.get(['orderedDate'])!.value
        ? moment(this.editForm.get(['orderedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      isExpired: this.editForm.get(['isExpired'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value ? moment(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      companyId: this.editForm.get(['companyId'])!.value,
      paketDetayId: this.editForm.get(['paketDetayId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderPaket>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
