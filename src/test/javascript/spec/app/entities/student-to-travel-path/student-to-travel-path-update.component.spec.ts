import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { StudentToTravelPathUpdateComponent } from 'app/entities/student-to-travel-path/student-to-travel-path-update.component';
import { StudentToTravelPathService } from 'app/entities/student-to-travel-path/student-to-travel-path.service';
import { StudentToTravelPath } from 'app/shared/model/student-to-travel-path.model';

describe('Component Tests', () => {
  describe('StudentToTravelPath Management Update Component', () => {
    let comp: StudentToTravelPathUpdateComponent;
    let fixture: ComponentFixture<StudentToTravelPathUpdateComponent>;
    let service: StudentToTravelPathService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [StudentToTravelPathUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StudentToTravelPathUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StudentToTravelPathUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StudentToTravelPathService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StudentToTravelPath(123);
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
        const entity = new StudentToTravelPath();
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
