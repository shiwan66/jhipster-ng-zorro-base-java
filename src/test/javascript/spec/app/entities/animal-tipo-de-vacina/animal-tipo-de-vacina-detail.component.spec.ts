import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalTipoDeVacinaDetailComponent } from 'app/entities/animal-tipo-de-vacina/animal-tipo-de-vacina-detail.component';
import { AnimalTipoDeVacina } from 'app/shared/model/animal-tipo-de-vacina.model';

describe('Component Tests', () => {
  describe('AnimalTipoDeVacina Management Detail Component', () => {
    let comp: AnimalTipoDeVacinaDetailComponent;
    let fixture: ComponentFixture<AnimalTipoDeVacinaDetailComponent>;
    const route = ({ data: of({ animalTipoDeVacina: new AnimalTipoDeVacina(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalTipoDeVacinaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnimalTipoDeVacinaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnimalTipoDeVacinaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load animalTipoDeVacina on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.animalTipoDeVacina).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
