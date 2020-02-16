import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITravelPath } from 'app/shared/model/travel-path.model';

@Component({
  selector: 'jhi-travel-path-detail',
  templateUrl: './travel-path-detail.component.html'
})
export class TravelPathDetailComponent implements OnInit {
  travelPath: ITravelPath | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ travelPath }) => (this.travelPath = travelPath));
  }

  previousState(): void {
    window.history.back();
  }
}
