import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalCarrapaticidaUpdateComponent } from 'app/entities/animal-carrapaticida/animal-carrapaticida-update.component';
import { AnimalCarrapaticidaService } from 'app/entities/animal-carrapaticida/animal-carrapaticida.service';
import { AnimalCarrapaticida } from 'app/shared/model/animal-carrapaticida.model';

describe('Component Tests', () => {
  describe('AnimalCarrapaticida Management Update Component', () => {
    let comp: AnimalCarrapaticidaUpdateComponent;
    let fixture: ComponentFixture<AnimalCarrapaticidaUpdateComponent>;
    let service: AnimalCarrapaticidaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalCarrapaticidaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnimalCarrapaticidaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnimalCarrapaticidaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnimalCarrapaticidaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnimalCarrapaticida(123);
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
        const entity = new AnimalCarrapaticida();
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
