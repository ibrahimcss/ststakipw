import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CustomerOfService } from 'app/entities/customer-of/customer-of.service';
import { ICustomerOf, CustomerOf } from 'app/shared/model/customer-of.model';
import { Sex } from 'app/shared/model/enumerations/sex.model';

describe('Service Tests', () => {
  describe('CustomerOf Service', () => {
    let injector: TestBed;
    let service: CustomerOfService;
    let httpMock: HttpTestingController;
    let elemDefault: ICustomerOf;
    let expectedResult: ICustomerOf | ICustomerOf[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CustomerOfService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CustomerOf(0, 0, 'AAAAAAA', false, Sex.Male);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CustomerOf', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CustomerOf()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CustomerOf', () => {
        const returnedFromService = Object.assign(
          {
            tcId: 1,
            phone: 'BBBBBB',
            enabled: true,
            sex: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CustomerOf', () => {
        const returnedFromService = Object.assign(
          {
            tcId: 1,
            phone: 'BBBBBB',
            enabled: true,
            sex: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CustomerOf', () => {
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
