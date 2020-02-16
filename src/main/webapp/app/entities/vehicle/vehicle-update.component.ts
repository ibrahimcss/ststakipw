import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVehicle, Vehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from './vehicle.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = ICompany | IUser;

@Component({
  selector: 'jhi-vehicle-update',
  templateUrl: './vehicle-update.component.html'
})
export class VehicleUpdateComponent implements OnInit {
  isSaving = false;
  companies: ICompany[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    licencePlate: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(20)]],
    brand: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
    model: [null, [Validators.required, Validators.min(2000)]],
    quota: [null, [Validators.required, Validators.min(1)]],
    enabled: [null, [Validators.required]],
    companyId: [null, Validators.required],
    credentialId: [null, Validators.required]
  });

  constructor(
    protected vehicleService: VehicleService,
    protected companyService: CompanyService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicle }) => {
      this.updateForm(vehicle);

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(vehicle: IVehicle): void {
    this.editForm.patchValue({
      id: vehicle.id,
      licencePlate: vehicle.licencePlate,
      brand: vehicle.brand,
      model: vehicle.model,
      quota: vehicle.quota,
      enabled: vehicle.enabled,
      companyId: vehicle.companyId,
      credentialId: vehicle.credentialId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicle = this.createFromForm();
    if (vehicle.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleService.update(vehicle));
    } else {
      this.subscribeToSaveResponse(this.vehicleService.create(vehicle));
    }
  }

  private createFromForm(): IVehicle {
    return {
      ...new Vehicle(),
      id: this.editForm.get(['id'])!.value,
      licencePlate: this.editForm.get(['licencePlate'])!.value,
      brand: this.editForm.get(['brand'])!.value,
      model: this.editForm.get(['model'])!.value,
      quota: this.editForm.get(['quota'])!.value,
      enabled: this.editForm.get(['enabled'])!.value,
      companyId: this.editForm.get(['companyId'])!.value,
      credentialId: this.editForm.get(['credentialId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicle>>): void {
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
