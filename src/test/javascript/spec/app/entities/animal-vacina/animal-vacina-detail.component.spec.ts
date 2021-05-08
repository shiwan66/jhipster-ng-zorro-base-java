import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalVacinaDetailComponent } from 'app/entities/animal-vacina/animal-vacina-detail.component';
import { AnimalVacina } from 'app/shared/model/animal-vacina.model';

describe('Component Tests', () => {
  describe('AnimalVacina Management Detail Component', () => {
    let comp: AnimalVacinaDetailComponent;
    let fixture: ComponentFixture<AnimalVacinaDetailComponent>;
    const route = ({ data: of({ animalVacina: new AnimalVacina(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalVacinaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnimalVacinaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnimalVacinaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load animalVacina on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.animalVacina).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
