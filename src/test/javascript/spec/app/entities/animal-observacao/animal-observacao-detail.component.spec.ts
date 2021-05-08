import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalObservacaoDetailComponent } from 'app/entities/animal-observacao/animal-observacao-detail.component';
import { AnimalObservacao } from 'app/shared/model/animal-observacao.model';

describe('Component Tests', () => {
  describe('AnimalObservacao Management Detail Component', () => {
    let comp: AnimalObservacaoDetailComponent;
    let fixture: ComponentFixture<AnimalObservacaoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ animalObservacao: new AnimalObservacao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalObservacaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnimalObservacaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnimalObservacaoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load animalObservacao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.animalObservacao).toEqual(jasmine.objectContaining({ id: 123 }));
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
