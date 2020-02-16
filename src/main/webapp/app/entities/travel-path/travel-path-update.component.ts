import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITravelPath, TravelPath } from 'app/shared/model/travel-path.model';
import { TravelPathService } from './travel-path.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = ICompany | IUser;

@Component({
  selector: 'jhi-travel-path-update',
  templateUrl: './travel-path-update.component.html'
})
export class TravelPathUpdateComponent implements OnInit {
  isSaving = false;
  companies: ICompany[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(250)]],
    departureTime: [null, [Validators.required]],
    departAddress: [null, [Validators.required]],
    departLon: [null, [Validators.required, Validators.min(-180), Validators.max(180)]],
    departLat: [null, [Validators.required, Validators.min(-90), Validators.max(90)]],
    arrivalTime: [null, [Validators.required]],
    arrivalAddress: [null, [Validators.required]],
    arrivalLon: [null, [Validators.required, Validators.min(-180), Validators.max(180)]],
    arrivalLat: [null, [Validators.required, Validators.min(-90), Validators.max(90)]],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    enabled: [null, [Validators.required]],
    companyId: [null, Validators.required],
    employeeId: [null, Validators.required]
  });

  constructor(
    protected travelPathService: TravelPathService,
    protected companyService: CompanyService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ travelPath }) => {
      if (!travelPath.id) {
        const today = moment().startOf('day');
        travelPath.departureTime = today;
        travelPath.arrivalTime = today;
        travelPath.startDate = today;
        travelPath.endDate = today;
      }

      this.updateForm(travelPath);

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(travelPath: ITravelPath): void {
    this.editForm.patchValue({
      id: travelPath.id,
      name: travelPath.name,
      departureTime: travelPath.departureTime ? travelPath.departureTime.format(DATE_TIME_FORMAT) : null,
      departAddress: travelPath.departAddress,
      departLon: travelPath.departLon,
      departLat: travelPath.departLat,
      arrivalTime: travelPath.arrivalTime ? travelPath.arrivalTime.format(DATE_TIME_FORMAT) : null,
      arrivalAddress: travelPath.arrivalAddress,
      arrivalLon: travelPath.arrivalLon,
      arrivalLat: travelPath.arrivalLat,
      startDate: travelPath.startDate ? travelPath.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: travelPath.endDate ? travelPath.endDate.format(DATE_TIME_FORMAT) : null,
      enabled: travelPath.enabled,
      companyId: travelPath.companyId,
      employeeId: travelPath.employeeId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const travelPath = this.createFromForm();
    if (travelPath.id !== undefined) {
      this.subscribeToSaveResponse(this.travelPathService.update(travelPath));
    } else {
      this.subscribeToSaveResponse(this.travelPathService.create(travelPath));
    }
  }

  private createFromForm(): ITravelPath {
    return {
      ...new TravelPath(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      departureTime: this.editForm.get(['departureTime'])!.value
        ? moment(this.editForm.get(['departureTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      departAddress: this.editForm.get(['departAddress'])!.value,
      departLon: this.editForm.get(['departLon'])!.value,
      departLat: this.editForm.get(['departLat'])!.value,
      arrivalTime: this.editForm.get(['arrivalTime'])!.value
        ? moment(this.editForm.get(['arrivalTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      arrivalAddress: this.editForm.get(['arrivalAddress'])!.value,
      arrivalLon: this.editForm.get(['arrivalLon'])!.value,
      arrivalLat: this.editForm.get(['arrivalLat'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value ? moment(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      enabled: this.editForm.get(['enabled'])!.value,
      companyId: this.editForm.get(['companyId'])!.value,
      employeeId: this.editForm.get(['employeeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITravelPath>>): void {
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
