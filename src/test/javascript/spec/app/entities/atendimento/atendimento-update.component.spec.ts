import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AtendimentoUpdateComponent } from 'app/entities/atendimento/atendimento-update.component';
import { AtendimentoService } from 'app/entities/atendimento/atendimento.service';
import { Atendimento } from 'app/shared/model/atendimento.model';

describe('Component Tests', () => {
  describe('Atendimento Management Update Component', () => {
    let comp: AtendimentoUpdateComponent;
    let fixture: ComponentFixture<AtendimentoUpdateComponent>;
    let service: AtendimentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AtendimentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AtendimentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AtendimentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AtendimentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Atendimento(123);
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
        const entity = new Atendimento();
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
