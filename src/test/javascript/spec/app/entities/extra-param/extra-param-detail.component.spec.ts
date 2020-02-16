import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StstakipTestModule } from '../../../test.module';
import { ExtraParamDetailComponent } from 'app/entities/extra-param/extra-param-detail.component';
import { ExtraParam } from 'app/shared/model/extra-param.model';

describe('Component Tests', () => {
  describe('ExtraParam Management Detail Component', () => {
    let comp: ExtraParamDetailComponent;
    let fixture: ComponentFixture<ExtraParamDetailComponent>;
    const route = ({ data: of({ extraParam: new ExtraParam(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [ExtraParamDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ExtraParamDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExtraParamDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load extraParam on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.extraParam).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
