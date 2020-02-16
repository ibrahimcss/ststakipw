import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IVehicleTracking } from 'app/shared/model/vehicle-tracking.model';

type EntityResponseType = HttpResponse<IVehicleTracking>;
type EntityArrayResponseType = HttpResponse<IVehicleTracking[]>;

@Injectable({ providedIn: 'root' })
export class VehicleTrackingService {
  public resourceUrl = SERVER_API_URL + 'api/vehicle-trackings';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/vehicle-trackings';

  constructor(protected http: HttpClient) {}

  create(vehicleTracking: IVehicleTracking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleTracking);
    return this.http
      .post<IVehicleTracking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehicleTracking: IVehicleTracking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleTracking);
    return this.http
      .put<IVehicleTracking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehicleTracking>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicleTracking[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicleTracking[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(vehicleTracking: IVehicleTracking): IVehicleTracking {
    const copy: IVehicleTracking = Object.assign({}, vehicleTracking, {
      createdAt: vehicleTracking.createdAt && vehicleTracking.createdAt.isValid() ? vehicleTracking.createdAt.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vehicleTracking: IVehicleTracking) => {
        vehicleTracking.createdAt = vehicleTracking.createdAt ? moment(vehicleTracking.createdAt) : undefined;
      });
    }
    return res;
  }
}
