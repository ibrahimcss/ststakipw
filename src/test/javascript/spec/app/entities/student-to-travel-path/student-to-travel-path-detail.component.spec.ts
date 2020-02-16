import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { StudentToTravelPathDetailComponent } from 'app/entities/student-to-travel-path/student-to-travel-path-detail.component';
import { StudentToTravelPath } from 'app/shared/model/student-to-travel-path.model';

describe('Component Tests', () => {
  describe('StudentToTravelPath Management Detail Component', () => {
    let comp: StudentToTravelPathDetailComponent;
    let fixture: ComponentFixture<StudentToTravelPathDetailComponent>;
    const route = ({ data: of({ studentToTravelPath: new StudentToTravelPath(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [StudentToTravelPathDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StudentToTravelPathDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StudentToTravelPathDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load studentToTravelPath on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.studentToTravelPath).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
