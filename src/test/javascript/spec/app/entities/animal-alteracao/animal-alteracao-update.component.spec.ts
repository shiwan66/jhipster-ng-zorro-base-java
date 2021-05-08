import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalAlteracaoUpdateComponent } from 'app/entities/animal-alteracao/animal-alteracao-update.component';
import { AnimalAlteracaoService } from 'app/entities/animal-alteracao/animal-alteracao.service';
import { AnimalAlteracao } from 'app/shared/model/animal-alteracao.model';

describe('Component Tests', () => {
  describe('AnimalAlteracao Management Update Component', () => {
    let comp: AnimalAlteracaoUpdateComponent;
    let fixture: ComponentFixture<AnimalAlteracaoUpdateComponent>;
    let service: AnimalAlteracaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalAlteracaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnimalAlteracaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnimalAlteracaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnimalAlteracaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnimalAlteracao(123);
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
        const entity = new AnimalAlteracao();
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
