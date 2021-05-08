import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalTipoDeVacinaUpdateComponent } from 'app/entities/animal-tipo-de-vacina/animal-tipo-de-vacina-update.component';
import { AnimalTipoDeVacinaService } from 'app/entities/animal-tipo-de-vacina/animal-tipo-de-vacina.service';
import { AnimalTipoDeVacina } from 'app/shared/model/animal-tipo-de-vacina.model';

describe('Component Tests', () => {
  describe('AnimalTipoDeVacina Management Update Component', () => {
    let comp: AnimalTipoDeVacinaUpdateComponent;
    let fixture: ComponentFixture<AnimalTipoDeVacinaUpdateComponent>;
    let service: AnimalTipoDeVacinaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalTipoDeVacinaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnimalTipoDeVacinaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnimalTipoDeVacinaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnimalTipoDeVacinaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnimalTipoDeVacina(123);
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
        const entity = new AnimalTipoDeVacina();
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
