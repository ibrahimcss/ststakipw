import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { VehicleTrackingDetailComponent } from 'app/entities/vehicle-tracking/vehicle-tracking-detail.component';
import { VehicleTracking } from 'app/shared/model/vehicle-tracking.model';

describe('Component Tests', () => {
  describe('VehicleTracking Management Detail Component', () => {
    let comp: VehicleTrackingDetailComponent;
    let fixture: ComponentFixture<VehicleTrackingDetailComponent>;
    const route = ({ data: of({ vehicleTracking: new VehicleTracking(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [VehicleTrackingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VehicleTrackingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehicleTrackingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vehicleTracking on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vehicleTracking).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
