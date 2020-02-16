import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { VehicleTrackingUpdateComponent } from 'app/entities/vehicle-tracking/vehicle-tracking-update.component';
import { VehicleTrackingService } from 'app/entities/vehicle-tracking/vehicle-tracking.service';
import { VehicleTracking } from 'app/shared/model/vehicle-tracking.model';

describe('Component Tests', () => {
  describe('VehicleTracking Management Update Component', () => {
    let comp: VehicleTrackingUpdateComponent;
    let fixture: ComponentFixture<VehicleTrackingUpdateComponent>;
    let service: VehicleTrackingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [VehicleTrackingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VehicleTrackingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleTrackingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehicleTrackingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VehicleTracking(123);
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
        const entity = new VehicleTracking();
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
