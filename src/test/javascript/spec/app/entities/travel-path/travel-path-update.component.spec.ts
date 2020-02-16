import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { TravelPathUpdateComponent } from 'app/entities/travel-path/travel-path-update.component';
import { TravelPathService } from 'app/entities/travel-path/travel-path.service';
import { TravelPath } from 'app/shared/model/travel-path.model';

describe('Component Tests', () => {
  describe('TravelPath Management Update Component', () => {
    let comp: TravelPathUpdateComponent;
    let fixture: ComponentFixture<TravelPathUpdateComponent>;
    let service: TravelPathService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [TravelPathUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TravelPathUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TravelPathUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TravelPathService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TravelPath(123);
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
        const entity = new TravelPath();
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
