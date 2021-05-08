import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { NgzorroTestModule } from '../../../test.module';
import { AnexoAtendimentoDetailComponent } from 'app/entities/anexo-atendimento/anexo-atendimento-detail.component';
import { AnexoAtendimento } from 'app/shared/model/anexo-atendimento.model';

describe('Component Tests', () => {
  describe('AnexoAtendimento Management Detail Component', () => {
    let comp: AnexoAtendimentoDetailComponent;
    let fixture: ComponentFixture<AnexoAtendimentoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ anexoAtendimento: new AnexoAtendimento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnexoAtendimentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnexoAtendimentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnexoAtendimentoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load anexoAtendimento on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.anexoAtendimento).toEqual(jasmine.objectContaining({ id: 123 }));
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
