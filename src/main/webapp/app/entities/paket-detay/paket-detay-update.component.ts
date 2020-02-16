import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPaketDetay, PaketDetay } from 'app/shared/model/paket-detay.model';
import { PaketDetayService } from './paket-detay.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-paket-detay-update',
  templateUrl: './paket-detay-update.component.html'
})
export class PaketDetayUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.required, Validators.minLength(5)]],
    price: [null, [Validators.min(1)]],
    vehicleQuota: [null, [Validators.min(5), Validators.max(1000)]],
    photo: [],
    photoContentType: [],
    enabled: [],
    year: [null, [Validators.required, Validators.min(1)]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected paketDetayService: PaketDetayService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paketDetay }) => {
      this.updateForm(paketDetay);
    });
  }

  updateForm(paketDetay: IPaketDetay): void {
    this.editForm.patchValue({
      id: paketDetay.id,
      name: paketDetay.name,
      description: paketDetay.description,
      price: paketDetay.price,
      vehicleQuota: paketDetay.vehicleQuota,
      photo: paketDetay.photo,
      photoContentType: paketDetay.photoContentType,
      enabled: paketDetay.enabled,
      year: paketDetay.year
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
    const paketDetay = this.createFromForm();
    if (paketDetay.id !== undefined) {
      this.subscribeToSaveResponse(this.paketDetayService.update(paketDetay));
    } else {
      this.subscribeToSaveResponse(this.paketDetayService.create(paketDetay));
    }
  }

  private createFromForm(): IPaketDetay {
    return {
      ...new PaketDetay(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      price: this.editForm.get(['price'])!.value,
      vehicleQuota: this.editForm.get(['vehicleQuota'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      enabled: this.editForm.get(['enabled'])!.value,
      year: this.editForm.get(['year'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaketDetay>>): void {
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
