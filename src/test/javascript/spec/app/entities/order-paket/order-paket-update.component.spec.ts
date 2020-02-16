import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { OrderPaketUpdateComponent } from 'app/entities/order-paket/order-paket-update.component';
import { OrderPaketService } from 'app/entities/order-paket/order-paket.service';
import { OrderPaket } from 'app/shared/model/order-paket.model';

describe('Component Tests', () => {
  describe('OrderPaket Management Update Component', () => {
    let comp: OrderPaketUpdateComponent;
    let fixture: ComponentFixture<OrderPaketUpdateComponent>;
    let service: OrderPaketService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [OrderPaketUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrderPaketUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderPaketUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderPaketService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderPaket(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderPaket();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
