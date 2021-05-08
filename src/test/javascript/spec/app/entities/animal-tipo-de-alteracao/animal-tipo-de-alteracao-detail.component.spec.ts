import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalTipoDeAlteracaoDetailComponent } from 'app/entities/animal-tipo-de-alteracao/animal-tipo-de-alteracao-detail.component';
import { AnimalTipoDeAlteracao } from 'app/shared/model/animal-tipo-de-alteracao.model';

describe('Component Tests', () => {
  describe('AnimalTipoDeAlteracao Management Detail Component', () => {
    let comp: AnimalTipoDeAlteracaoDetailComponent;
    let fixture: ComponentFixture<AnimalTipoDeAlteracaoDetailComponent>;
    const route = ({ data: of({ animalTipoDeAlteracao: new AnimalTipoDeAlteracao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalTipoDeAlteracaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnimalTipoDeAlteracaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnimalTipoDeAlteracaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load animalTipoDeAlteracao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.animalTipoDeAlteracao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
