import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { StstakipTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { WorksInDeleteDialogComponent } from 'app/entities/works-in/works-in-delete-dialog.component';
import { WorksInService } from 'app/entities/works-in/works-in.service';

describe('Component Tests', () => {
  describe('WorksIn Management Delete Component', () => {
    let comp: WorksInDeleteDialogComponent;
    let fixture: ComponentFixture<WorksInDeleteDialogComponent>;
    let service: WorksInService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [WorksInDeleteDialogComponent]
      })
        .overrideTemplate(WorksInDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorksInDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorksInService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
