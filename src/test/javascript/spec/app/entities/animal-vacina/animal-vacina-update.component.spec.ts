import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalVacinaUpdateComponent } from 'app/entities/animal-vacina/animal-vacina-update.component';
import { AnimalVacinaService } from 'app/entities/animal-vacina/animal-vacina.service';
import { AnimalVacina } from 'app/shared/model/animal-vacina.model';

describe('Component Tests', () => {
  describe('AnimalVacina Management Update Component', () => {
    let comp: AnimalVacinaUpdateComponent;
    let fixture: ComponentFixture<AnimalVacinaUpdateComponent>;
    let service: AnimalVacinaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalVacinaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnimalVacinaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnimalVacinaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnimalVacinaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnimalVacina(123);
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
        const entity = new AnimalVacina();
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
