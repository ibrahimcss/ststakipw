import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { PaketDetayUpdateComponent } from 'app/entities/paket-detay/paket-detay-update.component';
import { PaketDetayService } from 'app/entities/paket-detay/paket-detay.service';
import { PaketDetay } from 'app/shared/model/paket-detay.model';

describe('Component Tests', () => {
  describe('PaketDetay Management Update Component', () => {
    let comp: PaketDetayUpdateComponent;
    let fixture: ComponentFixture<PaketDetayUpdateComponent>;
    let service: PaketDetayService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [PaketDetayUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PaketDetayUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaketDetayUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaketDetayService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaketDetay(123);
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
        const entity = new PaketDetay();
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
