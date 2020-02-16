import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { StudentService } from 'app/entities/student/student.service';
import { IStudent, Student } from 'app/shared/model/student.model';
import { Sex } from 'app/shared/model/enumerations/sex.model';

describe('Service Tests', () => {
  describe('Student Service', () => {
    let injector: TestBed;
    let service: StudentService;
    let httpMock: HttpTestingController;
    let elemDefault: IStudent;
    let expectedResult: IStudent | IStudent[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(StudentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Student(0, 0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 0, 0, 'AAAAAAA', 'image/png', 'AAAAAAA', Sex.Male);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            birthDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Student', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            birthDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate
          },
          returnedFromService
        );

        service.create(new Student()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Student', () => {
        const returnedFromService = Object.assign(
          {
            tcId: 1,
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            birthDate: currentDate.format(DATE_TIME_FORMAT),
            address: 'BBBBBB',
            lon: 1,
            lat: 1,
            phone: 'BBBBBB',
            photo: 'BBBBBB',
            sex: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Student', () => {
        const returnedFromService = Object.assign(
          {
            tcId: 1,
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            birthDate: currentDate.format(DATE_TIME_FORMAT),
            address: 'BBBBBB',
            lon: 1,
            lat: 1,
            phone: 'BBBBBB',
            photo: 'BBBBBB',
            sex: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Student', () => {
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
