import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { OrderPaketDetailComponent } from 'app/entities/order-paket/order-paket-detail.component';
import { OrderPaket } from 'app/shared/model/order-paket.model';

describe('Component Tests', () => {
  describe('OrderPaket Management Detail Component', () => {
    let comp: OrderPaketDetailComponent;
    let fixture: ComponentFixture<OrderPaketDetailComponent>;
    const route = ({ data: of({ orderPaket: new OrderPaket(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [OrderPaketDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrderPaketDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderPaketDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load orderPaket on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderPaket).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
