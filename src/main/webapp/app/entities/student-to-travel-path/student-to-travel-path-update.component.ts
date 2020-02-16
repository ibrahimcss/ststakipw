import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IStudentToTravelPath, StudentToTravelPath } from 'app/shared/model/student-to-travel-path.model';
import { StudentToTravelPathService } from './student-to-travel-path.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student/student.service';
import { ITravelPath } from 'app/shared/model/travel-path.model';
import { TravelPathService } from 'app/entities/travel-path/travel-path.service';

type SelectableEntity = IStudent | ITravelPath;

@Component({
  selector: 'jhi-student-to-travel-path-update',
  templateUrl: './student-to-travel-path-update.component.html'
})
export class StudentToTravelPathUpdateComponent implements OnInit {
  isSaving = false;
  students: IStudent[] = [];
  travelpaths: ITravelPath[] = [];

  editForm = this.fb.group({
    id: [],
    boardingTimeOfArrival: [null, [Validators.required]],
    landingTimeOfArrival: [null, [Validators.required]],
    boardingTimeOfReturn: [null, [Validators.required]],
    landingTimeOfReturn: [null, [Validators.required]],
    enabled: [null, [Validators.required]],
    studentId: [null, Validators.required],
    travelPathId: [null, Validators.required]
  });

  constructor(
    protected studentToTravelPathService: StudentToTravelPathService,
    protected studentService: StudentService,
    protected travelPathService: TravelPathService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ studentToTravelPath }) => {
      if (!studentToTravelPath.id) {
        const today = moment().startOf('day');
        studentToTravelPath.boardingTimeOfArrival = today;
        studentToTravelPath.landingTimeOfArrival = today;
        studentToTravelPath.boardingTimeOfReturn = today;
        studentToTravelPath.landingTimeOfReturn = today;
      }

      this.updateForm(studentToTravelPath);

      this.studentService.query().subscribe((res: HttpResponse<IStudent[]>) => (this.students = res.body || []));

      this.travelPathService.query().subscribe((res: HttpResponse<ITravelPath[]>) => (this.travelpaths = res.body || []));
    });
  }

  updateForm(studentToTravelPath: IStudentToTravelPath): void {
    this.editForm.patchValue({
      id: studentToTravelPath.id,
      boardingTimeOfArrival: studentToTravelPath.boardingTimeOfArrival
        ? studentToTravelPath.boardingTimeOfArrival.format(DATE_TIME_FORMAT)
        : null,
      landingTimeOfArrival: studentToTravelPath.landingTimeOfArrival
        ? studentToTravelPath.landingTimeOfArrival.format(DATE_TIME_FORMAT)
        : null,
      boardingTimeOfReturn: studentToTravelPath.boardingTimeOfReturn
        ? studentToTravelPath.boardingTimeOfReturn.format(DATE_TIME_FORMAT)
        : null,
      landingTimeOfReturn: studentToTravelPath.landingTimeOfReturn
        ? studentToTravelPath.landingTimeOfReturn.format(DATE_TIME_FORMAT)
        : null,
      enabled: studentToTravelPath.enabled,
      studentId: studentToTravelPath.studentId,
      travelPathId: studentToTravelPath.travelPathId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const studentToTravelPath = this.createFromForm();
    if (studentToTravelPath.id !== undefined) {
      this.subscribeToSaveResponse(this.studentToTravelPathService.update(studentToTravelPath));
    } else {
      this.subscribeToSaveResponse(this.studentToTravelPathService.create(studentToTravelPath));
    }
  }

  private createFromForm(): IStudentToTravelPath {
    return {
      ...new StudentToTravelPath(),
      id: this.editForm.get(['id'])!.value,
      boardingTimeOfArrival: this.editForm.get(['boardingTimeOfArrival'])!.value
        ? moment(this.editForm.get(['boardingTimeOfArrival'])!.value, DATE_TIME_FORMAT)
        : undefined,
      landingTimeOfArrival: this.editForm.get(['landingTimeOfArrival'])!.value
        ? moment(this.editForm.get(['landingTimeOfArrival'])!.value, DATE_TIME_FORMAT)
        : undefined,
      boardingTimeOfReturn: this.editForm.get(['boardingTimeOfReturn'])!.value
        ? moment(this.editForm.get(['boardingTimeOfReturn'])!.value, DATE_TIME_FORMAT)
        : undefined,
      landingTimeOfReturn: this.editForm.get(['landingTimeOfReturn'])!.value
        ? moment(this.editForm.get(['landingTimeOfReturn'])!.value, DATE_TIME_FORMAT)
        : undefined,
      enabled: this.editForm.get(['enabled'])!.value,
      studentId: this.editForm.get(['studentId'])!.value,
      travelPathId: this.editForm.get(['travelPathId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentToTravelPath>>): void {
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
