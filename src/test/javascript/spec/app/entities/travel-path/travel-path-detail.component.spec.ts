import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { TravelPathDetailComponent } from 'app/entities/travel-path/travel-path-detail.component';
import { TravelPath } from 'app/shared/model/travel-path.model';

describe('Component Tests', () => {
  describe('TravelPath Management Detail Component', () => {
    let comp: TravelPathDetailComponent;
    let fixture: ComponentFixture<TravelPathDetailComponent>;
    const route = ({ data: of({ travelPath: new TravelPath(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [TravelPathDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TravelPathDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TravelPathDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load travelPath on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.travelPath).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
