import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OrderPaketService } from 'app/entities/order-paket/order-paket.service';
import { IOrderPaket, OrderPaket } from 'app/shared/model/order-paket.model';

describe('Service Tests', () => {
  describe('OrderPaket Service', () => {
    let injector: TestBed;
    let service: OrderPaketService;
    let httpMock: HttpTestingController;
    let elemDefault: IOrderPaket;
    let expectedResult: IOrderPaket | IOrderPaket[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(OrderPaketService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new OrderPaket(0, currentDate, false, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            orderedDate: currentDate.format(DATE_TIME_FORMAT),
            startDate: currentDate.format(DATE_TIME_FORMAT),
            endDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a OrderPaket', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            orderedDate: currentDate.format(DATE_TIME_FORMAT),
            startDate: currentDate.format(DATE_TIME_FORMAT),
            endDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            orderedDate: currentDate,
            startDate: currentDate,
            endDate: currentDate
          },
          returnedFromService
        );

        service.create(new OrderPaket()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a OrderPaket', () => {
        const returnedFromService = Object.assign(
          {
            orderedDate: currentDate.format(DATE_TIME_FORMAT),
            isExpired: true,
            startDate: currentDate.format(DATE_TIME_FORMAT),
            endDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            orderedDate: currentDate,
            startDate: currentDate,
            endDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of OrderPaket', () => {
        const returnedFromService = Object.assign(
          {
            orderedDate: currentDate.format(DATE_TIME_FORMAT),
            isExpired: true,
            startDate: currentDate.format(DATE_TIME_FORMAT),
            endDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            orderedDate: currentDate,
            startDate: currentDate,
            endDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a OrderPaket', () => {
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
