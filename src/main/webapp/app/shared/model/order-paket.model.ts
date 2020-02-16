import { Moment } from 'moment';

export interface IOrderPaket {
  id?: number;
  orderedDate?: Moment;
  isExpired?: boolean;
  startDate?: Moment;
  endDate?: Moment;
  companyName?: string;
  companyId?: number;
  paketDetayName?: string;
  paketDetayId?: number;
}

export class OrderPaket implements IOrderPaket {
  constructor(
    public id?: number,
    public orderedDate?: Moment,
    public isExpired?: boolean,
    public startDate?: Moment,
    public endDate?: Moment,
    public companyName?: string,
    public companyId?: number,
    public paketDetayName?: string,
    public paketDetayId?: number
  ) {
    this.isExpired = this.isExpired || false;
  }
}
