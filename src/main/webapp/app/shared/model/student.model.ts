import { Moment } from 'moment';
import { Sex } from 'app/shared/model/enumerations/sex.model';

export interface IStudent {
  id?: number;
  tcId?: number;
  firstName?: string;
  lastName?: string;
  birthDate?: Moment;
  address?: string;
  lon?: number;
  lat?: number;
  phone?: string;
  photoContentType?: string;
  photo?: any;
  sex?: Sex;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public tcId?: number,
    public firstName?: string,
    public lastName?: string,
    public birthDate?: Moment,
    public address?: string,
    public lon?: number,
    public lat?: number,
    public phone?: string,
    public photoContentType?: string,
    public photo?: any,
    public sex?: Sex
  ) {}
}
