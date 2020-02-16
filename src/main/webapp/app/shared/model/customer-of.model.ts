import { Sex } from 'app/shared/model/enumerations/sex.model';

export interface ICustomerOf {
  id?: number;
  tcId?: number;
  phone?: string;
  enabled?: boolean;
  sex?: Sex;
  customerLogin?: string;
  customerId?: number;
  companyName?: string;
  companyId?: number;
}

export class CustomerOf implements ICustomerOf {
  constructor(
    public id?: number,
    public tcId?: number,
    public phone?: string,
    public enabled?: boolean,
    public sex?: Sex,
    public customerLogin?: string,
    public customerId?: number,
    public companyName?: string,
    public companyId?: number
  ) {
    this.enabled = this.enabled || false;
  }
}
