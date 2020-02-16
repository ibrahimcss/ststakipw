import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ITravelPath } from 'app/shared/model/travel-path.model';

type EntityResponseType = HttpResponse<ITravelPath>;
type EntityArrayResponseType = HttpResponse<ITravelPath[]>;

@Injectable({ providedIn: 'root' })
export class TravelPathService {
  public resourceUrl = SERVER_API_URL + 'api/travel-paths';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/travel-paths';

  constructor(protected http: HttpClient) {}

  create(travelPath: ITravelPath): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(travelPath);
    return this.http
      .post<ITravelPath>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(travelPath: ITravelPath): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(travelPath);
    return this.http
      .put<ITravelPath>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITravelPath>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITravelPath[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITravelPath[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(travelPath: ITravelPath): ITravelPath {
    const copy: ITravelPath = Object.assign({}, travelPath, {
      departureTime: travelPath.departureTime && travelPath.departureTime.isValid() ? travelPath.departureTime.toJSON() : undefined,
      arrivalTime: travelPath.arrivalTime && travelPath.arrivalTime.isValid() ? travelPath.arrivalTime.toJSON() : undefined,
      startDate: travelPath.startDate && travelPath.startDate.isValid() ? travelPath.startDate.toJSON() : undefined,
      endDate: travelPath.endDate && travelPath.endDate.isValid() ? travelPath.endDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.departureTime = res.body.departureTime ? moment(res.body.departureTime) : undefined;
      res.body.arrivalTime = res.body.arrivalTime ? moment(res.body.arrivalTime) : undefined;
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((travelPath: ITravelPath) => {
        travelPath.departureTime = travelPath.departureTime ? moment(travelPath.departureTime) : undefined;
        travelPath.arrivalTime = travelPath.arrivalTime ? moment(travelPath.arrivalTime) : undefined;
        travelPath.startDate = travelPath.startDate ? moment(travelPath.startDate) : undefined;
        travelPath.endDate = travelPath.endDate ? moment(travelPath.endDate) : undefined;
      });
    }
    return res;
  }
}
