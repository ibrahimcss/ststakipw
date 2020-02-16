import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IPaketDetay } from 'app/shared/model/paket-detay.model';

type EntityResponseType = HttpResponse<IPaketDetay>;
type EntityArrayResponseType = HttpResponse<IPaketDetay[]>;

@Injectable({ providedIn: 'root' })
export class PaketDetayService {
  public resourceUrl = SERVER_API_URL + 'api/paket-detays';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/paket-detays';

  constructor(protected http: HttpClient) {}

  create(paketDetay: IPaketDetay): Observable<EntityResponseType> {
    return this.http.post<IPaketDetay>(this.resourceUrl, paketDetay, { observe: 'response' });
  }

  update(paketDetay: IPaketDetay): Observable<EntityResponseType> {
    return this.http.put<IPaketDetay>(this.resourceUrl, paketDetay, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaketDetay>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaketDetay[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaketDetay[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
