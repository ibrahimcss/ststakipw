import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IOrderPaket } from 'app/shared/model/order-paket.model';

type EntityResponseType = HttpResponse<IOrderPaket>;
type EntityArrayResponseType = HttpResponse<IOrderPaket[]>;

@Injectable({ providedIn: 'root' })
export class OrderPaketService {
  public resourceUrl = SERVER_API_URL + 'api/order-pakets';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/order-pakets';

  constructor(protected http: HttpClient) {}

  create(orderPaket: IOrderPaket): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderPaket);
    return this.http
      .post<IOrderPaket>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orderPaket: IOrderPaket): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderPaket);
    return this.http
      .put<IOrderPaket>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrderPaket>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderPaket[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderPaket[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(orderPaket: IOrderPaket): IOrderPaket {
    const copy: IOrderPaket = Object.assign({}, orderPaket, {
      orderedDate: orderPaket.orderedDate && orderPaket.orderedDate.isValid() ? orderPaket.orderedDate.toJSON() : undefined,
      startDate: orderPaket.startDate && orderPaket.startDate.isValid() ? orderPaket.startDate.toJSON() : undefined,
      endDate: orderPaket.endDate && orderPaket.endDate.isValid() ? orderPaket.endDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.orderedDate = res.body.orderedDate ? moment(res.body.orderedDate) : undefined;
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((orderPaket: IOrderPaket) => {
        orderPaket.orderedDate = orderPaket.orderedDate ? moment(orderPaket.orderedDate) : undefined;
        orderPaket.startDate = orderPaket.startDate ? moment(orderPaket.startDate) : undefined;
        orderPaket.endDate = orderPaket.endDate ? moment(orderPaket.endDate) : undefined;
      });
    }
    return res;
  }
}
