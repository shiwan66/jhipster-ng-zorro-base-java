import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalVeterinarioDetailComponent } from 'app/entities/animal-veterinario/animal-veterinario-detail.component';
import { AnimalVeterinario } from 'app/shared/model/animal-veterinario.model';

describe('Component Tests', () => {
  describe('AnimalVeterinario Management Detail Component', () => {
    let comp: AnimalVeterinarioDetailComponent;
    let fixture: ComponentFixture<AnimalVeterinarioDetailComponent>;
    const route = ({ data: of({ animalVeterinario: new AnimalVeterinario(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalVeterinarioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnimalVeterinarioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnimalVeterinarioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load animalVeterinario on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.animalVeterinario).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
