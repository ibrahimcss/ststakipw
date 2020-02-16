import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICompany, Company } from 'app/shared/model/company.model';
import { CompanyService } from './company.service';

@Component({
  selector: 'jhi-company-update',
  templateUrl: './company-update.component.html'
})
export class CompanyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(250)]],
    phone: [null, [Validators.required, Validators.pattern('^\\+90[0-9]{10}$')]],
    email: [null, [Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    addressLine: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(250)]],
    city: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
    postalCode: [],
    country: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
    url: [null, [Validators.maxLength(250)]],
    lon: [null, [Validators.required, Validators.min(-180), Validators.max(180)]],
    lat: [null, [Validators.required, Validators.min(-90), Validators.max(90)]]
  });

  constructor(protected companyService: CompanyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ company }) => {
      this.updateForm(company);
    });
  }

  updateForm(company: ICompany): void {
    this.editForm.patchValue({
      id: company.id,
      name: company.name,
      phone: company.phone,
      email: company.email,
      addressLine: company.addressLine,
      city: company.city,
      postalCode: company.postalCode,
      country: company.country,
      url: company.url,
      lon: company.lon,
      lat: company.lat
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const company = this.createFromForm();
    if (company.id !== undefined) {
      this.subscribeToSaveResponse(this.companyService.update(company));
    } else {
      this.subscribeToSaveResponse(this.companyService.create(company));
    }
  }

  private createFromForm(): ICompany {
    return {
      ...new Company(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      email: this.editForm.get(['email'])!.value,
      addressLine: this.editForm.get(['addressLine'])!.value,
      city: this.editForm.get(['city'])!.value,
      postalCode: this.editForm.get(['postalCode'])!.value,
      country: this.editForm.get(['country'])!.value,
      url: this.editForm.get(['url'])!.value,
      lon: this.editForm.get(['lon'])!.value,
      lat: this.editForm.get(['lat'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompany>>): void {
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
}
