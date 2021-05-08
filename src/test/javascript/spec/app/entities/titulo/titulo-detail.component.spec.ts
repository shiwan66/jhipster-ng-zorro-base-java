import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { NgzorroTestModule } from '../../../test.module';
import { TituloDetailComponent } from 'app/entities/titulo/titulo-detail.component';
import { Titulo } from 'app/shared/model/titulo.model';

describe('Component Tests', () => {
  describe('Titulo Management Detail Component', () => {
    let comp: TituloDetailComponent;
    let fixture: ComponentFixture<TituloDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ titulo: new Titulo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [TituloDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TituloDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TituloDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load titulo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.titulo).toEqual(jasmine.objectContaining({ id: 123 }));
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
