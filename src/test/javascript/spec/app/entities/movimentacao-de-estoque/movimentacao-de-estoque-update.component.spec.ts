import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { MovimentacaoDeEstoqueUpdateComponent } from 'app/entities/movimentacao-de-estoque/movimentacao-de-estoque-update.component';
import { MovimentacaoDeEstoqueService } from 'app/entities/movimentacao-de-estoque/movimentacao-de-estoque.service';
import { MovimentacaoDeEstoque } from 'app/shared/model/movimentacao-de-estoque.model';

describe('Component Tests', () => {
  describe('MovimentacaoDeEstoque Management Update Component', () => {
    let comp: MovimentacaoDeEstoqueUpdateComponent;
    let fixture: ComponentFixture<MovimentacaoDeEstoqueUpdateComponent>;
    let service: MovimentacaoDeEstoqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [MovimentacaoDeEstoqueUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MovimentacaoDeEstoqueUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MovimentacaoDeEstoqueUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MovimentacaoDeEstoqueService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MovimentacaoDeEstoque(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MovimentacaoDeEstoque();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
