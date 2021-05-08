import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalVeterinarioUpdateComponent } from 'app/entities/animal-veterinario/animal-veterinario-update.component';
import { AnimalVeterinarioService } from 'app/entities/animal-veterinario/animal-veterinario.service';
import { AnimalVeterinario } from 'app/shared/model/animal-veterinario.model';

describe('Component Tests', () => {
  describe('AnimalVeterinario Management Update Component', () => {
    let comp: AnimalVeterinarioUpdateComponent;
    let fixture: ComponentFixture<AnimalVeterinarioUpdateComponent>;
    let service: AnimalVeterinarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalVeterinarioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnimalVeterinarioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnimalVeterinarioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnimalVeterinarioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnimalVeterinario(123);
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
        const entity = new AnimalVeterinario();
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
