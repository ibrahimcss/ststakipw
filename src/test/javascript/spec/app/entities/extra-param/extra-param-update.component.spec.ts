import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { ExtraParamUpdateComponent } from 'app/entities/extra-param/extra-param-update.component';
import { ExtraParamService } from 'app/entities/extra-param/extra-param.service';
import { ExtraParam } from 'app/shared/model/extra-param.model';

describe('Component Tests', () => {
  describe('ExtraParam Management Update Component', () => {
    let comp: ExtraParamUpdateComponent;
    let fixture: ComponentFixture<ExtraParamUpdateComponent>;
    let service: ExtraParamService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [ExtraParamUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ExtraParamUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExtraParamUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExtraParamService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExtraParam(123);
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
        const entity = new ExtraParam();
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
