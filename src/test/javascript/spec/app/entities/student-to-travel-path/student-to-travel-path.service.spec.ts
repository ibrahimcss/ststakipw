import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { StudentToTravelPathService } from 'app/entities/student-to-travel-path/student-to-travel-path.service';
import { IStudentToTravelPath, StudentToTravelPath } from 'app/shared/model/student-to-travel-path.model';

describe('Service Tests', () => {
  describe('StudentToTravelPath Service', () => {
    let injector: TestBed;
    let service: StudentToTravelPathService;
    let httpMock: HttpTestingController;
    let elemDefault: IStudentToTravelPath;
    let expectedResult: IStudentToTravelPath | IStudentToTravelPath[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(StudentToTravelPathService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new StudentToTravelPath(0, currentDate, currentDate, currentDate, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            boardingTimeOfArrival: currentDate.format(DATE_TIME_FORMAT),
            landingTimeOfArrival: currentDate.format(DATE_TIME_FORMAT),
            boardingTimeOfReturn: currentDate.format(DATE_TIME_FORMAT),
            landingTimeOfReturn: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a StudentToTravelPath', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            boardingTimeOfArrival: currentDate.format(DATE_TIME_FORMAT),
            landingTimeOfArrival: currentDate.format(DATE_TIME_FORMAT),
            boardingTimeOfReturn: currentDate.format(DATE_TIME_FORMAT),
            landingTimeOfReturn: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            boardingTimeOfArrival: currentDate,
            landingTimeOfArrival: currentDate,
            boardingTimeOfReturn: currentDate,
            landingTimeOfReturn: currentDate
          },
          returnedFromService
        );

        service.create(new StudentToTravelPath()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a StudentToTravelPath', () => {
        const returnedFromService = Object.assign(
          {
            boardingTimeOfArrival: currentDate.format(DATE_TIME_FORMAT),
            landingTimeOfArrival: currentDate.format(DATE_TIME_FORMAT),
            boardingTimeOfReturn: currentDate.format(DATE_TIME_FORMAT),
            landingTimeOfReturn: currentDate.format(DATE_TIME_FORMAT),
            enabled: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            boardingTimeOfArrival: currentDate,
            landingTimeOfArrival: currentDate,
            boardingTimeOfReturn: currentDate,
            landingTimeOfReturn: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of StudentToTravelPath', () => {
        const returnedFromService = Object.assign(
          {
            boardingTimeOfArrival: currentDate.format(DATE_TIME_FORMAT),
            landingTimeOfArrival: currentDate.format(DATE_TIME_FORMAT),
            boardingTimeOfReturn: currentDate.format(DATE_TIME_FORMAT),
            landingTimeOfReturn: currentDate.format(DATE_TIME_FORMAT),
            enabled: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            boardingTimeOfArrival: currentDate,
            landingTimeOfArrival: currentDate,
            boardingTimeOfReturn: currentDate,
            landingTimeOfReturn: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a StudentToTravelPath', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
