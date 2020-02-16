import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IStudent, Student } from 'app/shared/model/student.model';
import { StudentService } from './student.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html'
})
export class StudentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tcId: [null, [Validators.required, Validators.min(10000000000), Validators.max(99999999999)]],
    firstName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    lastName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    birthDate: [null, [Validators.required]],
    address: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(250)]],
    lon: [null, [Validators.required, Validators.min(-180), Validators.max(180)]],
    lat: [null, [Validators.required, Validators.min(-90), Validators.max(90)]],
    phone: [null, [Validators.pattern('^\\+90[0-9]{10}$')]],
    photo: [],
    photoContentType: [],
    sex: [null, [Validators.required]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected studentService: StudentService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ student }) => {
      if (!student.id) {
        const today = moment().startOf('day');
        student.birthDate = today;
      }

      this.updateForm(student);
    });
  }

  updateForm(student: IStudent): void {
    this.editForm.patchValue({
      id: student.id,
      tcId: student.tcId,
      firstName: student.firstName,
      lastName: student.lastName,
      birthDate: student.birthDate ? student.birthDate.format(DATE_TIME_FORMAT) : null,
      address: student.address,
      lon: student.lon,
      lat: student.lat,
      phone: student.phone,
      photo: student.photo,
      photoContentType: student.photoContentType,
      sex: student.sex
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
    const student = this.createFromForm();
    if (student.id !== undefined) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  private createFromForm(): IStudent {
    return {
      ...new Student(),
      id: this.editForm.get(['id'])!.value,
      tcId: this.editForm.get(['tcId'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value ? moment(this.editForm.get(['birthDate'])!.value, DATE_TIME_FORMAT) : undefined,
      address: this.editForm.get(['address'])!.value,
      lon: this.editForm.get(['lon'])!.value,
      lat: this.editForm.get(['lat'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      sex: this.editForm.get(['sex'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>): void {
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
