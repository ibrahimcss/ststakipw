import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { WorksInDetailComponent } from 'app/entities/works-in/works-in-detail.component';
import { WorksIn } from 'app/shared/model/works-in.model';

describe('Component Tests', () => {
  describe('WorksIn Management Detail Component', () => {
    let comp: WorksInDetailComponent;
    let fixture: ComponentFixture<WorksInDetailComponent>;
    const route = ({ data: of({ worksIn: new WorksIn(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [WorksInDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(WorksInDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorksInDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load worksIn on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.worksIn).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
