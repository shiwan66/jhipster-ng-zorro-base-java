import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalCarrapaticidaDetailComponent } from 'app/entities/animal-carrapaticida/animal-carrapaticida-detail.component';
import { AnimalCarrapaticida } from 'app/shared/model/animal-carrapaticida.model';

describe('Component Tests', () => {
  describe('AnimalCarrapaticida Management Detail Component', () => {
    let comp: AnimalCarrapaticidaDetailComponent;
    let fixture: ComponentFixture<AnimalCarrapaticidaDetailComponent>;
    const route = ({ data: of({ animalCarrapaticida: new AnimalCarrapaticida(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalCarrapaticidaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnimalCarrapaticidaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnimalCarrapaticidaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load animalCarrapaticida on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.animalCarrapaticida).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
