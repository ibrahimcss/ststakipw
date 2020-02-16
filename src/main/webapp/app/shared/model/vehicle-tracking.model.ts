import { Moment } from 'moment';

export interface IVehicleTracking {
  id?: number;
  createdAt?: Moment;
  lon?: number;
  lat?: number;
  vehicleLicencePlate?: string;
  vehicleId?: number;
  companyName?: string;
  companyId?: number;
}

export class VehicleTracking implements IVehicleTracking {
  constructor(
    public id?: number,
    public createdAt?: Moment,
    public lon?: number,
    public lat?: number,
    public vehicleLicencePlate?: string,
    public vehicleId?: number,
    public companyName?: string,
    public companyId?: number
  ) {}
}
