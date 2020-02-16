import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { WorksInUpdateComponent } from 'app/entities/works-in/works-in-update.component';
import { WorksInService } from 'app/entities/works-in/works-in.service';
import { WorksIn } from 'app/shared/model/works-in.model';

describe('Component Tests', () => {
  describe('WorksIn Management Update Component', () => {
    let comp: WorksInUpdateComponent;
    let fixture: ComponentFixture<WorksInUpdateComponent>;
    let service: WorksInService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [WorksInUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(WorksInUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorksInUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorksInService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorksIn(123);
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
        const entity = new WorksIn();
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
