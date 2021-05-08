import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnexoAtendimentoUpdateComponent } from 'app/entities/anexo-atendimento/anexo-atendimento-update.component';
import { AnexoAtendimentoService } from 'app/entities/anexo-atendimento/anexo-atendimento.service';
import { AnexoAtendimento } from 'app/shared/model/anexo-atendimento.model';

describe('Component Tests', () => {
  describe('AnexoAtendimento Management Update Component', () => {
    let comp: AnexoAtendimentoUpdateComponent;
    let fixture: ComponentFixture<AnexoAtendimentoUpdateComponent>;
    let service: AnexoAtendimentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnexoAtendimentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnexoAtendimentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnexoAtendimentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnexoAtendimentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnexoAtendimento(123);
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
        const entity = new AnexoAtendimento();
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
