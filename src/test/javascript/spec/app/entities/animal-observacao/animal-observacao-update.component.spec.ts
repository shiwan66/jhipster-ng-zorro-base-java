import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalObservacaoUpdateComponent } from 'app/entities/animal-observacao/animal-observacao-update.component';
import { AnimalObservacaoService } from 'app/entities/animal-observacao/animal-observacao.service';
import { AnimalObservacao } from 'app/shared/model/animal-observacao.model';

describe('Component Tests', () => {
  describe('AnimalObservacao Management Update Component', () => {
    let comp: AnimalObservacaoUpdateComponent;
    let fixture: ComponentFixture<AnimalObservacaoUpdateComponent>;
    let service: AnimalObservacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalObservacaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnimalObservacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnimalObservacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnimalObservacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnimalObservacao(123);
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
        const entity = new AnimalObservacao();
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
