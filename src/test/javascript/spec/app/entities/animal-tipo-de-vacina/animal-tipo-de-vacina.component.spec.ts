import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalTipoDeVacinaComponent } from 'app/entities/animal-tipo-de-vacina/animal-tipo-de-vacina.component';
import { AnimalTipoDeVacinaService } from 'app/entities/animal-tipo-de-vacina/animal-tipo-de-vacina.service';
import { AnimalTipoDeVacina } from 'app/shared/model/animal-tipo-de-vacina.model';

describe('Component Tests', () => {
  describe('AnimalTipoDeVacina Management Component', () => {
    let comp: AnimalTipoDeVacinaComponent;
    let fixture: ComponentFixture<AnimalTipoDeVacinaComponent>;
    let service: AnimalTipoDeVacinaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalTipoDeVacinaComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(AnimalTipoDeVacinaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnimalTipoDeVacinaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnimalTipoDeVacinaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AnimalTipoDeVacina(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.animalTipoDeVacinas && comp.animalTipoDeVacinas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AnimalTipoDeVacina(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.animalTipoDeVacinas && comp.animalTipoDeVacinas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page is the page is the same as the previous page', () => {
      spyOn(service, 'query').and.callThrough();

      // WHEN
      comp.ngOnInit();
      comp.loadPage(0);

      // THEN
      expect(service.query).toHaveBeenCalledTimes(1);
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
