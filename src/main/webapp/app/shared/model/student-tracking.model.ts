import { Moment } from 'moment';
import { TrackingStep } from 'app/shared/model/enumerations/tracking-step.model';

export interface IStudentTracking {
  id?: number;
  createdAt?: Moment;
  photoContentType?: string;
  photo?: any;
  lon?: number;
  lat?: number;
  tStep?: TrackingStep;
  studentToTravelPathId?: number;
  vehicleLicencePlate?: string;
  vehicleId?: number;
  studentTcId?: string;
  studentId?: number;
}

export class StudentTracking implements IStudentTracking {
  constructor(
    public id?: number,
    public createdAt?: Moment,
    public photoContentType?: string,
    public photo?: any,
    public lon?: number,
    public lat?: number,
    public tStep?: TrackingStep,
    public studentToTravelPathId?: number,
    public vehicleLicencePlate?: string,
    public vehicleId?: number,
    public studentTcId?: string,
    public studentId?: number
  ) {}
}
