import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { StudentTrackingUpdateComponent } from 'app/entities/student-tracking/student-tracking-update.component';
import { StudentTrackingService } from 'app/entities/student-tracking/student-tracking.service';
import { StudentTracking } from 'app/shared/model/student-tracking.model';

describe('Component Tests', () => {
  describe('StudentTracking Management Update Component', () => {
    let comp: StudentTrackingUpdateComponent;
    let fixture: ComponentFixture<StudentTrackingUpdateComponent>;
    let service: StudentTrackingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [StudentTrackingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StudentTrackingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StudentTrackingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StudentTrackingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StudentTracking(123);
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
        const entity = new StudentTracking();
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
