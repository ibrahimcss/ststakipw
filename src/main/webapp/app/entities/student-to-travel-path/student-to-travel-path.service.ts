import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IStudentToTravelPath } from 'app/shared/model/student-to-travel-path.model';

type EntityResponseType = HttpResponse<IStudentToTravelPath>;
type EntityArrayResponseType = HttpResponse<IStudentToTravelPath[]>;

@Injectable({ providedIn: 'root' })
export class StudentToTravelPathService {
  public resourceUrl = SERVER_API_URL + 'api/student-to-travel-paths';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/student-to-travel-paths';

  constructor(protected http: HttpClient) {}

  create(studentToTravelPath: IStudentToTravelPath): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(studentToTravelPath);
    return this.http
      .post<IStudentToTravelPath>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(studentToTravelPath: IStudentToTravelPath): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(studentToTravelPath);
    return this.http
      .put<IStudentToTravelPath>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStudentToTravelPath>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStudentToTravelPath[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStudentToTravelPath[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(studentToTravelPath: IStudentToTravelPath): IStudentToTravelPath {
    const copy: IStudentToTravelPath = Object.assign({}, studentToTravelPath, {
      boardingTimeOfArrival:
        studentToTravelPath.boardingTimeOfArrival && studentToTravelPath.boardingTimeOfArrival.isValid()
          ? studentToTravelPath.boardingTimeOfArrival.toJSON()
          : undefined,
      landingTimeOfArrival:
        studentToTravelPath.landingTimeOfArrival && studentToTravelPath.landingTimeOfArrival.isValid()
          ? studentToTravelPath.landingTimeOfArrival.toJSON()
          : undefined,
      boardingTimeOfReturn:
        studentToTravelPath.boardingTimeOfReturn && studentToTravelPath.boardingTimeOfReturn.isValid()
          ? studentToTravelPath.boardingTimeOfReturn.toJSON()
          : undefined,
      landingTimeOfReturn:
        studentToTravelPath.landingTimeOfReturn && studentToTravelPath.landingTimeOfReturn.isValid()
          ? studentToTravelPath.landingTimeOfReturn.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.boardingTimeOfArrival = res.body.boardingTimeOfArrival ? moment(res.body.boardingTimeOfArrival) : undefined;
      res.body.landingTimeOfArrival = res.body.landingTimeOfArrival ? moment(res.body.landingTimeOfArrival) : undefined;
      res.body.boardingTimeOfReturn = res.body.boardingTimeOfReturn ? moment(res.body.boardingTimeOfReturn) : undefined;
      res.body.landingTimeOfReturn = res.body.landingTimeOfReturn ? moment(res.body.landingTimeOfReturn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((studentToTravelPath: IStudentToTravelPath) => {
        studentToTravelPath.boardingTimeOfArrival = studentToTravelPath.boardingTimeOfArrival
          ? moment(studentToTravelPath.boardingTimeOfArrival)
          : undefined;
        studentToTravelPath.landingTimeOfArrival = studentToTravelPath.landingTimeOfArrival
          ? moment(studentToTravelPath.landingTimeOfArrival)
          : undefined;
        studentToTravelPath.boardingTimeOfReturn = studentToTravelPath.boardingTimeOfReturn
          ? moment(studentToTravelPath.boardingTimeOfReturn)
          : undefined;
        studentToTravelPath.landingTimeOfReturn = studentToTravelPath.landingTimeOfReturn
          ? moment(studentToTravelPath.landingTimeOfReturn)
          : undefined;
      });
    }
    return res;
  }
}
