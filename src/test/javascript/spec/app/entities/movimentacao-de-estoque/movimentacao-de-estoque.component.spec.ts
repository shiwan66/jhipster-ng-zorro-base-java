import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { NgzorroTestModule } from '../../../test.module';
import { MovimentacaoDeEstoqueComponent } from 'app/entities/movimentacao-de-estoque/movimentacao-de-estoque.component';
import { MovimentacaoDeEstoqueService } from 'app/entities/movimentacao-de-estoque/movimentacao-de-estoque.service';
import { MovimentacaoDeEstoque } from 'app/shared/model/movimentacao-de-estoque.model';

describe('Component Tests', () => {
  describe('MovimentacaoDeEstoque Management Component', () => {
    let comp: MovimentacaoDeEstoqueComponent;
    let fixture: ComponentFixture<MovimentacaoDeEstoqueComponent>;
    let service: MovimentacaoDeEstoqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [MovimentacaoDeEstoqueComponent],
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
        .overrideTemplate(MovimentacaoDeEstoqueComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MovimentacaoDeEstoqueComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MovimentacaoDeEstoqueService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MovimentacaoDeEstoque(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.movimentacaoDeEstoques && comp.movimentacaoDeEstoques[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MovimentacaoDeEstoque(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.movimentacaoDeEstoques && comp.movimentacaoDeEstoques[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
