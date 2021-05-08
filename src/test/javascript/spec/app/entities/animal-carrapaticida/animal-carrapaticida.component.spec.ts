import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalCarrapaticidaComponent } from 'app/entities/animal-carrapaticida/animal-carrapaticida.component';
import { AnimalCarrapaticidaService } from 'app/entities/animal-carrapaticida/animal-carrapaticida.service';
import { AnimalCarrapaticida } from 'app/shared/model/animal-carrapaticida.model';

describe('Component Tests', () => {
  describe('AnimalCarrapaticida Management Component', () => {
    let comp: AnimalCarrapaticidaComponent;
    let fixture: ComponentFixture<AnimalCarrapaticidaComponent>;
    let service: AnimalCarrapaticidaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalCarrapaticidaComponent],
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
        .overrideTemplate(AnimalCarrapaticidaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnimalCarrapaticidaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnimalCarrapaticidaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AnimalCarrapaticida(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.animalCarrapaticidas && comp.animalCarrapaticidas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AnimalCarrapaticida(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.animalCarrapaticidas && comp.animalCarrapaticidas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
