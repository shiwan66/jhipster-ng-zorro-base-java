import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalCioUpdateComponent } from 'app/entities/animal-cio/animal-cio-update.component';
import { AnimalCioService } from 'app/entities/animal-cio/animal-cio.service';
import { AnimalCio } from 'app/shared/model/animal-cio.model';

describe('Component Tests', () => {
  describe('AnimalCio Management Update Component', () => {
    let comp: AnimalCioUpdateComponent;
    let fixture: ComponentFixture<AnimalCioUpdateComponent>;
    let service: AnimalCioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalCioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnimalCioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnimalCioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnimalCioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnimalCio(123);
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
        const entity = new AnimalCio();
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
