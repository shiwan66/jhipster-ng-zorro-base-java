import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { ModeloAtividadeUpdateComponent } from 'app/entities/modelo-atividade/modelo-atividade-update.component';
import { ModeloAtividadeService } from 'app/entities/modelo-atividade/modelo-atividade.service';
import { ModeloAtividade } from 'app/shared/model/modelo-atividade.model';

describe('Component Tests', () => {
  describe('ModeloAtividade Management Update Component', () => {
    let comp: ModeloAtividadeUpdateComponent;
    let fixture: ComponentFixture<ModeloAtividadeUpdateComponent>;
    let service: ModeloAtividadeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [ModeloAtividadeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ModeloAtividadeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ModeloAtividadeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModeloAtividadeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ModeloAtividade(123);
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
        const entity = new ModeloAtividade();
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
