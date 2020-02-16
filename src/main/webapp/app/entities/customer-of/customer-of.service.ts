import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICustomerOf } from 'app/shared/model/customer-of.model';

type EntityResponseType = HttpResponse<ICustomerOf>;
type EntityArrayResponseType = HttpResponse<ICustomerOf[]>;

@Injectable({ providedIn: 'root' })
export class CustomerOfService {
  public resourceUrl = SERVER_API_URL + 'api/customer-ofs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/customer-ofs';

  constructor(protected http: HttpClient) {}

  create(customerOf: ICustomerOf): Observable<EntityResponseType> {
    return this.http.post<ICustomerOf>(this.resourceUrl, customerOf, { observe: 'response' });
  }

  update(customerOf: ICustomerOf): Observable<EntityResponseType> {
    return this.http.put<ICustomerOf>(this.resourceUrl, customerOf, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomerOf>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomerOf[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomerOf[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
