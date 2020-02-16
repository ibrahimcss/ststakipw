import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { CustomerOfDetailComponent } from 'app/entities/customer-of/customer-of-detail.component';
import { CustomerOf } from 'app/shared/model/customer-of.model';

describe('Component Tests', () => {
  describe('CustomerOf Management Detail Component', () => {
    let comp: CustomerOfDetailComponent;
    let fixture: ComponentFixture<CustomerOfDetailComponent>;
    const route = ({ data: of({ customerOf: new CustomerOf(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [CustomerOfDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CustomerOfDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerOfDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load customerOf on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customerOf).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
