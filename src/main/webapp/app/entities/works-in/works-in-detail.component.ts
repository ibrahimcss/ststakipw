import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorksIn } from 'app/shared/model/works-in.model';

@Component({
  selector: 'jhi-works-in-detail',
  templateUrl: './works-in-detail.component.html'
})
export class WorksInDetailComponent implements OnInit {
  worksIn: IWorksIn | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ worksIn }) => (this.worksIn = worksIn));
  }

  previousState(): void {
    window.history.back();
  }
}
