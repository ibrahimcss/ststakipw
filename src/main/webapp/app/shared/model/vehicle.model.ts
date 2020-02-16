export interface IVehicle {
  id?: number;
  licencePlate?: string;
  brand?: string;
  model?: number;
  quota?: number;
  enabled?: boolean;
  companyName?: string;
  companyId?: number;
  credentialLogin?: string;
  credentialId?: number;
}

export class Vehicle implements IVehicle {
  constructor(
    public id?: number,
    public licencePlate?: string,
    public brand?: string,
    public model?: number,
    public quota?: number,
    public enabled?: boolean,
    public companyName?: string,
    public companyId?: number,
    public credentialLogin?: string,
    public credentialId?: number
  ) {
    this.enabled = this.enabled || false;
  }
}
