import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { StstakipTestModule } from '../../../test.module';
import { PaketDetayDetailComponent } from 'app/entities/paket-detay/paket-detay-detail.component';
import { PaketDetay } from 'app/shared/model/paket-detay.model';

describe('Component Tests', () => {
  describe('PaketDetay Management Detail Component', () => {
    let comp: PaketDetayDetailComponent;
    let fixture: ComponentFixture<PaketDetayDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ paketDetay: new PaketDetay(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StstakipTestModule],
        declarations: [PaketDetayDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PaketDetayDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaketDetayDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load paketDetay on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paketDetay).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
