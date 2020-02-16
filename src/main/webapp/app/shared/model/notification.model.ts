import { Moment } from 'moment';
import { NotifyType } from 'app/shared/model/enumerations/notify-type.model';

export interface INotification {
  id?: number;
  title?: string;
  message?: string;
  type?: NotifyType;
  createdAt?: Moment;
  urlImage?: string;
  isRead?: boolean;
  travelPathName?: string;
  travelPathId?: number;
}

export class Notification implements INotification {
  constructor(
    public id?: number,
    public title?: string,
    public message?: string,
    public type?: NotifyType,
    public createdAt?: Moment,
    public urlImage?: string,
    public isRead?: boolean,
    public travelPathName?: string,
    public travelPathId?: number
  ) {
    this.isRead = this.isRead || false;
  }
}
