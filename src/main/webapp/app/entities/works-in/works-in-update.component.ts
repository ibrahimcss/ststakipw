import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorksIn, WorksIn } from 'app/shared/model/works-in.model';
import { WorksInService } from './works-in.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

type SelectableEntity = IUser | ICompany;

@Component({
  selector: 'jhi-works-in-update',
  templateUrl: './works-in-update.component.html'
})
export class WorksInUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  companies: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    tcId: [null, [Validators.required, Validators.min(10000000000), Validators.max(99999999999)]],
    phone: [null, [Validators.required, Validators.pattern('^\\+90[0-9]{10}$')]],
    enabled: [null, [Validators.required]],
    employeeId: [null, Validators.required],
    companyId: [null, Validators.required]
  });

  constructor(
    protected worksInService: WorksInService,
    protected userService: UserService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ worksIn }) => {
      this.updateForm(worksIn);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));
    });
  }

  updateForm(worksIn: IWorksIn): void {
    this.editForm.patchValue({
      id: worksIn.id,
      tcId: worksIn.tcId,
      phone: worksIn.phone,
      enabled: worksIn.enabled,
      employeeId: worksIn.employeeId,
      companyId: worksIn.companyId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const worksIn = this.createFromForm();
    if (worksIn.id !== undefined) {
      this.subscribeToSaveResponse(this.worksInService.update(worksIn));
    } else {
      this.subscribeToSaveResponse(this.worksInService.create(worksIn));
    }
  }

  private createFromForm(): IWorksIn {
    return {
      ...new WorksIn(),
      id: this.editForm.get(['id'])!.value,
      tcId: this.editForm.get(['tcId'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      enabled: this.editForm.get(['enabled'])!.value,
      employeeId: this.editForm.get(['employeeId'])!.value,
      companyId: this.editForm.get(['companyId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorksIn>>): void {
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
