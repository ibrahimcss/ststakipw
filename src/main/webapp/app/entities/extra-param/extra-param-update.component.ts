import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IExtraParam, ExtraParam } from 'app/shared/model/extra-param.model';
import { ExtraParamService } from './extra-param.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

@Component({
  selector: 'jhi-extra-param-update',
  templateUrl: './extra-param-update.component.html'
})
export class ExtraParamUpdateComponent implements OnInit {
  isSaving = false;
  companies: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(250)]],
    value: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(250)]],
    companyId: [null, Validators.required]
  });

  constructor(
    protected extraParamService: ExtraParamService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ extraParam }) => {
      this.updateForm(extraParam);

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));
    });
  }

  updateForm(extraParam: IExtraParam): void {
    this.editForm.patchValue({
      id: extraParam.id,
      key: extraParam.key,
      value: extraParam.value,
      companyId: extraParam.companyId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const extraParam = this.createFromForm();
    if (extraParam.id !== undefined) {
      this.subscribeToSaveResponse(this.extraParamService.update(extraParam));
    } else {
      this.subscribeToSaveResponse(this.extraParamService.create(extraParam));
    }
  }

  private createFromForm(): IExtraParam {
    return {
      ...new ExtraParam(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      value: this.editForm.get(['value'])!.value,
      companyId: this.editForm.get(['companyId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExtraParam>>): void {
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

  trackById(index: number, item: ICompany): any {
    return item.id;
  }
}
