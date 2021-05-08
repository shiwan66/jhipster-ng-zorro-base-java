import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalAlteracaoDetailComponent } from 'app/entities/animal-alteracao/animal-alteracao-detail.component';
import { AnimalAlteracao } from 'app/shared/model/animal-alteracao.model';

describe('Component Tests', () => {
  describe('AnimalAlteracao Management Detail Component', () => {
    let comp: AnimalAlteracaoDetailComponent;
    let fixture: ComponentFixture<AnimalAlteracaoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ animalAlteracao: new AnimalAlteracao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalAlteracaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnimalAlteracaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnimalAlteracaoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load animalAlteracao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.animalAlteracao).toEqual(jasmine.objectContaining({ id: 123 }));
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
