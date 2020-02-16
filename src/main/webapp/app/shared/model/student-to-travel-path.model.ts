import { Moment } from 'moment';

export interface IStudentToTravelPath {
  id?: number;
  boardingTimeOfArrival?: Moment;
  landingTimeOfArrival?: Moment;
  boardingTimeOfReturn?: Moment;
  landingTimeOfReturn?: Moment;
  enabled?: boolean;
  studentTcId?: string;
  studentId?: number;
  travelPathName?: string;
  travelPathId?: number;
}

export class StudentToTravelPath implements IStudentToTravelPath {
  constructor(
    public id?: number,
    public boardingTimeOfArrival?: Moment,
    public landingTimeOfArrival?: Moment,
    public boardingTimeOfReturn?: Moment,
    public landingTimeOfReturn?: Moment,
    public enabled?: boolean,
    public studentTcId?: string,
    public studentId?: number,
    public travelPathName?: string,
    public travelPathId?: number
  ) {
    this.enabled = this.enabled || false;
  }
}
