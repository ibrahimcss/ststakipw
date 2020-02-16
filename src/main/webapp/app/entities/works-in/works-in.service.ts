import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IWorksIn } from 'app/shared/model/works-in.model';

type EntityResponseType = HttpResponse<IWorksIn>;
type EntityArrayResponseType = HttpResponse<IWorksIn[]>;

@Injectable({ providedIn: 'root' })
export class WorksInService {
  public resourceUrl = SERVER_API_URL + 'api/works-ins';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/works-ins';

  constructor(protected http: HttpClient) {}

  create(worksIn: IWorksIn): Observable<EntityResponseType> {
    return this.http.post<IWorksIn>(this.resourceUrl, worksIn, { observe: 'response' });
  }

  update(worksIn: IWorksIn): Observable<EntityResponseType> {
    return this.http.put<IWorksIn>(this.resourceUrl, worksIn, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWorksIn>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorksIn[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorksIn[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
