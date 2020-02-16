import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { StstakipTestModule } from '../../../test.module';
import { VehicleTrackingComponent } from 'app/entities/vehicle-tracking/vehicle-tracking.component';
import { VehicleTrackingService } from 'app/entities/vehicle-tracking/vehicle-tracking.service';
import { VehicleTracking } from 'app/shared/model/vehicle-tracking.model';

describe('Component Tests', () => {
  describe('VehicleTracking Management Component', () => {
    let comp: VehicleTrackingComponent;
    let fixture: ComponentFixture<VehicleTrackingComponent>;
    let service: VehicleTrackingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [VehicleTrackingComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(VehicleTrackingComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleTrackingComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehicleTrackingService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VehicleTracking(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vehicleTrackings && comp.vehicleTrackings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VehicleTracking(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vehicleTrackings && comp.vehicleTrackings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
