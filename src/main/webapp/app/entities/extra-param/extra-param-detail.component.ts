import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExtraParam } from 'app/shared/model/extra-param.model';

@Component({
  selector: 'jhi-extra-param-detail',
  templateUrl: './extra-param-detail.component.html'
})
export class ExtraParamDetailComponent implements OnInit {
  extraParam: IExtraParam | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ extraParam }) => (this.extraParam = extraParam));
  }

  previousState(): void {
    window.history.back();
  }
}
