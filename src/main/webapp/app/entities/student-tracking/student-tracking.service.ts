import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IStudentTracking } from 'app/shared/model/student-tracking.model';

type EntityResponseType = HttpResponse<IStudentTracking>;
type EntityArrayResponseType = HttpResponse<IStudentTracking[]>;

@Injectable({ providedIn: 'root' })
export class StudentTrackingService {
  public resourceUrl = SERVER_API_URL + 'api/student-trackings';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/student-trackings';

  constructor(protected http: HttpClient) {}

  create(studentTracking: IStudentTracking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(studentTracking);
    return this.http
      .post<IStudentTracking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(studentTracking: IStudentTracking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(studentTracking);
    return this.http
      .put<IStudentTracking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStudentTracking>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStudentTracking[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStudentTracking[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(studentTracking: IStudentTracking): IStudentTracking {
    const copy: IStudentTracking = Object.assign({}, studentTracking, {
      createdAt: studentTracking.createdAt && studentTracking.createdAt.isValid() ? studentTracking.createdAt.toJSON() : undefined
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
      res.body.forEach((studentTracking: IStudentTracking) => {
        studentTracking.createdAt = studentTracking.createdAt ? moment(studentTracking.createdAt) : undefined;
      });
    }
    return res;
  }
}
