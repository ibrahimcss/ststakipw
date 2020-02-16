import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVehicleTracking, VehicleTracking } from 'app/shared/model/vehicle-tracking.model';
import { VehicleTrackingService } from './vehicle-tracking.service';
import { IVehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from 'app/entities/vehicle/vehicle.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

type SelectableEntity = IVehicle | ICompany;

@Component({
  selector: 'jhi-vehicle-tracking-update',
  templateUrl: './vehicle-tracking-update.component.html'
})
export class VehicleTrackingUpdateComponent implements OnInit {
  isSaving = false;
  vehicles: IVehicle[] = [];
  companies: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    createdAt: [null, [Validators.required]],
    lon: [null, [Validators.required, Validators.min(-180), Validators.max(180)]],
    lat: [null, [Validators.required, Validators.min(-90), Validators.max(90)]],
    vehicleId: [null, Validators.required],
    companyId: [null, Validators.required]
  });

  constructor(
    protected vehicleTrackingService: VehicleTrackingService,
    protected vehicleService: VehicleService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleTracking }) => {
      if (!vehicleTracking.id) {
        const today = moment().startOf('day');
        vehicleTracking.createdAt = today;
      }

      this.updateForm(vehicleTracking);

      this.vehicleService.query().subscribe((res: HttpResponse<IVehicle[]>) => (this.vehicles = res.body || []));

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));
    });
  }

  updateForm(vehicleTracking: IVehicleTracking): void {
    this.editForm.patchValue({
      id: vehicleTracking.id,
      createdAt: vehicleTracking.createdAt ? vehicleTracking.createdAt.format(DATE_TIME_FORMAT) : null,
      lon: vehicleTracking.lon,
      lat: vehicleTracking.lat,
      vehicleId: vehicleTracking.vehicleId,
      companyId: vehicleTracking.companyId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicleTracking = this.createFromForm();
    if (vehicleTracking.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleTrackingService.update(vehicleTracking));
    } else {
      this.subscribeToSaveResponse(this.vehicleTrackingService.create(vehicleTracking));
    }
  }

  private createFromForm(): IVehicleTracking {
    return {
      ...new VehicleTracking(),
      id: this.editForm.get(['id'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      lon: this.editForm.get(['lon'])!.value,
      lat: this.editForm.get(['lat'])!.value,
      vehicleId: this.editForm.get(['vehicleId'])!.value,
      companyId: this.editForm.get(['companyId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleTracking>>): void {
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
