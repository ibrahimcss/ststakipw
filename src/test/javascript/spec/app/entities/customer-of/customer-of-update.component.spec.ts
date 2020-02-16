import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { CustomerOfUpdateComponent } from 'app/entities/customer-of/customer-of-update.component';
import { CustomerOfService } from 'app/entities/customer-of/customer-of.service';
import { CustomerOf } from 'app/shared/model/customer-of.model';

describe('Component Tests', () => {
  describe('CustomerOf Management Update Component', () => {
    let comp: CustomerOfUpdateComponent;
    let fixture: ComponentFixture<CustomerOfUpdateComponent>;
    let service: CustomerOfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [CustomerOfUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CustomerOfUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerOfUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerOfService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerOf(123);
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
        const entity = new CustomerOf();
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
