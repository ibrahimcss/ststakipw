import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IStudentTracking, StudentTracking } from 'app/shared/model/student-tracking.model';
import { StudentTrackingService } from './student-tracking.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IStudentToTravelPath } from 'app/shared/model/student-to-travel-path.model';
import { StudentToTravelPathService } from 'app/entities/student-to-travel-path/student-to-travel-path.service';
import { IVehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from 'app/entities/vehicle/vehicle.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student/student.service';

type SelectableEntity = IStudentToTravelPath | IVehicle | IStudent;

@Component({
  selector: 'jhi-student-tracking-update',
  templateUrl: './student-tracking-update.component.html'
})
export class StudentTrackingUpdateComponent implements OnInit {
  isSaving = false;
  studenttotravelpaths: IStudentToTravelPath[] = [];
  vehicles: IVehicle[] = [];
  students: IStudent[] = [];

  editForm = this.fb.group({
    id: [],
    createdAt: [null, [Validators.required]],
    photo: [],
    photoContentType: [],
    lon: [null, [Validators.required, Validators.min(-180), Validators.max(180)]],
    lat: [null, [Validators.required, Validators.min(-90), Validators.max(90)]],
    tStep: [null, [Validators.required]],
    studentToTravelPathId: [null, Validators.required],
    vehicleId: [null, Validators.required],
    studentId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected studentTrackingService: StudentTrackingService,
    protected studentToTravelPathService: StudentToTravelPathService,
    protected vehicleService: VehicleService,
    protected studentService: StudentService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ studentTracking }) => {
      if (!studentTracking.id) {
        const today = moment().startOf('day');
        studentTracking.createdAt = today;
      }

      this.updateForm(studentTracking);

      this.studentToTravelPathService
        .query()
        .subscribe((res: HttpResponse<IStudentToTravelPath[]>) => (this.studenttotravelpaths = res.body || []));

      this.vehicleService.query().subscribe((res: HttpResponse<IVehicle[]>) => (this.vehicles = res.body || []));

      this.studentService.query().subscribe((res: HttpResponse<IStudent[]>) => (this.students = res.body || []));
    });
  }

  updateForm(studentTracking: IStudentTracking): void {
    this.editForm.patchValue({
      id: studentTracking.id,
      createdAt: studentTracking.createdAt ? studentTracking.createdAt.format(DATE_TIME_FORMAT) : null,
      photo: studentTracking.photo,
      photoContentType: studentTracking.photoContentType,
      lon: studentTracking.lon,
      lat: studentTracking.lat,
      tStep: studentTracking.tStep,
      studentToTravelPathId: studentTracking.studentToTravelPathId,
      vehicleId: studentTracking.vehicleId,
      studentId: studentTracking.studentId
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('ststakipApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const studentTracking = this.createFromForm();
    if (studentTracking.id !== undefined) {
      this.subscribeToSaveResponse(this.studentTrackingService.update(studentTracking));
    } else {
      this.subscribeToSaveResponse(this.studentTrackingService.create(studentTracking));
    }
  }

  private createFromForm(): IStudentTracking {
    return {
      ...new StudentTracking(),
      id: this.editForm.get(['id'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      lon: this.editForm.get(['lon'])!.value,
      lat: this.editForm.get(['lat'])!.value,
      tStep: this.editForm.get(['tStep'])!.value,
      studentToTravelPathId: this.editForm.get(['studentToTravelPathId'])!.value,
      vehicleId: this.editForm.get(['vehicleId'])!.value,
      studentId: this.editForm.get(['studentId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentTracking>>): void {
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
