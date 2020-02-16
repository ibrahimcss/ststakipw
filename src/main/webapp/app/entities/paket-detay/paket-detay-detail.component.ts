import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPaketDetay } from 'app/shared/model/paket-detay.model';

@Component({
  selector: 'jhi-paket-detay-detail',
  templateUrl: './paket-detay-detail.component.html'
})
export class PaketDetayDetailComponent implements OnInit {
  paketDetay: IPaketDetay | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paketDetay }) => (this.paketDetay = paketDetay));
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
