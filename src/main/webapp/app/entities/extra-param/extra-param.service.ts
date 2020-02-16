import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IExtraParam } from 'app/shared/model/extra-param.model';

type EntityResponseType = HttpResponse<IExtraParam>;
type EntityArrayResponseType = HttpResponse<IExtraParam[]>;

@Injectable({ providedIn: 'root' })
export class ExtraParamService {
  public resourceUrl = SERVER_API_URL + 'api/extra-params';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/extra-params';

  constructor(protected http: HttpClient) {}

  create(extraParam: IExtraParam): Observable<EntityResponseType> {
    return this.http.post<IExtraParam>(this.resourceUrl, extraParam, { observe: 'response' });
  }

  update(extraParam: IExtraParam): Observable<EntityResponseType> {
    return this.http.put<IExtraParam>(this.resourceUrl, extraParam, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IExtraParam>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExtraParam[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExtraParam[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
