import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { INotification, Notification } from 'app/shared/model/notification.model';
import { NotificationService } from './notification.service';
import { ITravelPath } from 'app/shared/model/travel-path.model';
import { TravelPathService } from 'app/entities/travel-path/travel-path.service';

@Component({
  selector: 'jhi-notification-update',
  templateUrl: './notification-update.component.html'
})
export class NotificationUpdateComponent implements OnInit {
  isSaving = false;
  travelpaths: ITravelPath[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    message: [null, [Validators.required]],
    type: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    urlImage: [null, [Validators.maxLength(250)]],
    isRead: [],
    travelPathId: []
  });

  constructor(
    protected notificationService: NotificationService,
    protected travelPathService: TravelPathService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notification }) => {
      if (!notification.id) {
        const today = moment().startOf('day');
        notification.createdAt = today;
      }

      this.updateForm(notification);

      this.travelPathService.query().subscribe((res: HttpResponse<ITravelPath[]>) => (this.travelpaths = res.body || []));
    });
  }

  updateForm(notification: INotification): void {
    this.editForm.patchValue({
      id: notification.id,
      title: notification.title,
      message: notification.message,
      type: notification.type,
      createdAt: notification.createdAt ? notification.createdAt.format(DATE_TIME_FORMAT) : null,
      urlImage: notification.urlImage,
      isRead: notification.isRead,
      travelPathId: notification.travelPathId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notification = this.createFromForm();
    if (notification.id !== undefined) {
      this.subscribeToSaveResponse(this.notificationService.update(notification));
    } else {
      this.subscribeToSaveResponse(this.notificationService.create(notification));
    }
  }

  private createFromForm(): INotification {
    return {
      ...new Notification(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      message: this.editForm.get(['message'])!.value,
      type: this.editForm.get(['type'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      urlImage: this.editForm.get(['urlImage'])!.value,
      isRead: this.editForm.get(['isRead'])!.value,
      travelPathId: this.editForm.get(['travelPathId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotification>>): void {
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

  trackById(index: number, item: ITravelPath): any {
    return item.id;
  }
}
