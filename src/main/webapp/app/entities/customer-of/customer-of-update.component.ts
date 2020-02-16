import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICustomerOf, CustomerOf } from 'app/shared/model/customer-of.model';
import { CustomerOfService } from './customer-of.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

type SelectableEntity = IUser | ICompany;

@Component({
  selector: 'jhi-customer-of-update',
  templateUrl: './customer-of-update.component.html'
})
export class CustomerOfUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  companies: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    tcId: [null, [Validators.required, Validators.min(10000000000), Validators.max(99999999999)]],
    phone: [null, [Validators.required, Validators.pattern('^\\+90[0-9]{10}$')]],
    enabled: [null, [Validators.required]],
    sex: [null, [Validators.required]],
    customerId: [null, Validators.required],
    companyId: [null, Validators.required]
  });

  constructor(
    protected customerOfService: CustomerOfService,
    protected userService: UserService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerOf }) => {
      this.updateForm(customerOf);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));
    });
  }

  updateForm(customerOf: ICustomerOf): void {
    this.editForm.patchValue({
      id: customerOf.id,
      tcId: customerOf.tcId,
      phone: customerOf.phone,
      enabled: customerOf.enabled,
      sex: customerOf.sex,
      customerId: customerOf.customerId,
      companyId: customerOf.companyId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customerOf = this.createFromForm();
    if (customerOf.id !== undefined) {
      this.subscribeToSaveResponse(this.customerOfService.update(customerOf));
    } else {
      this.subscribeToSaveResponse(this.customerOfService.create(customerOf));
    }
  }

  private createFromForm(): ICustomerOf {
    return {
      ...new CustomerOf(),
      id: this.editForm.get(['id'])!.value,
      tcId: this.editForm.get(['tcId'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      enabled: this.editForm.get(['enabled'])!.value,
      sex: this.editForm.get(['sex'])!.value,
      customerId: this.editForm.get(['customerId'])!.value,
      companyId: this.editForm.get(['companyId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerOf>>): void {
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
