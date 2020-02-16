import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IStudentTracking } from 'app/shared/model/student-tracking.model';

@Component({
  selector: 'jhi-student-tracking-detail',
  templateUrl: './student-tracking-detail.component.html'
})
export class StudentTrackingDetailComponent implements OnInit {
  studentTracking: IStudentTracking | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ studentTracking }) => (this.studentTracking = studentTracking));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
