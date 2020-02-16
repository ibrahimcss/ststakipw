import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentToTravelPath } from 'app/shared/model/student-to-travel-path.model';

@Component({
  selector: 'jhi-student-to-travel-path-detail',
  templateUrl: './student-to-travel-path-detail.component.html'
})
export class StudentToTravelPathDetailComponent implements OnInit {
  studentToTravelPath: IStudentToTravelPath | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ studentToTravelPath }) => (this.studentToTravelPath = studentToTravelPath));
  }

  previousState(): void {
    window.history.back();
  }
}
