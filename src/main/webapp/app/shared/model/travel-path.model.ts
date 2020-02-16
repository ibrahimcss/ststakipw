import { Moment } from 'moment';

export interface ITravelPath {
  id?: number;
  name?: string;
  departureTime?: Moment;
  departAddress?: string;
  departLon?: number;
  departLat?: number;
  arrivalTime?: Moment;
  arrivalAddress?: string;
  arrivalLon?: number;
  arrivalLat?: number;
  startDate?: Moment;
  endDate?: Moment;
  enabled?: boolean;
  companyName?: string;
  companyId?: number;
  employeeLogin?: string;
  employeeId?: number;
}

export class TravelPath implements ITravelPath {
  constructor(
    public id?: number,
    public name?: string,
    public departureTime?: Moment,
    public departAddress?: string,
    public departLon?: number,
    public departLat?: number,
    public arrivalTime?: Moment,
    public arrivalAddress?: string,
    public arrivalLon?: number,
    public arrivalLat?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public enabled?: boolean,
    public companyName?: string,
    public companyId?: number,
    public employeeLogin?: string,
    public employeeId?: number
  ) {
    this.enabled = this.enabled || false;
  }
}
