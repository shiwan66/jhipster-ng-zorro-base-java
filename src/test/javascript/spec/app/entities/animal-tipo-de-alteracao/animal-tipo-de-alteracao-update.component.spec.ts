import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalTipoDeAlteracaoUpdateComponent } from 'app/entities/animal-tipo-de-alteracao/animal-tipo-de-alteracao-update.component';
import { AnimalTipoDeAlteracaoService } from 'app/entities/animal-tipo-de-alteracao/animal-tipo-de-alteracao.service';
import { AnimalTipoDeAlteracao } from 'app/shared/model/animal-tipo-de-alteracao.model';

describe('Component Tests', () => {
  describe('AnimalTipoDeAlteracao Management Update Component', () => {
    let comp: AnimalTipoDeAlteracaoUpdateComponent;
    let fixture: ComponentFixture<AnimalTipoDeAlteracaoUpdateComponent>;
    let service: AnimalTipoDeAlteracaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalTipoDeAlteracaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnimalTipoDeAlteracaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnimalTipoDeAlteracaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnimalTipoDeAlteracaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnimalTipoDeAlteracao(123);
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
        const entity = new AnimalTipoDeAlteracao();
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
