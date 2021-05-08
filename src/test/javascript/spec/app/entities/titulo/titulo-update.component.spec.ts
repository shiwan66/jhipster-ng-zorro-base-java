import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { TituloUpdateComponent } from 'app/entities/titulo/titulo-update.component';
import { TituloService } from 'app/entities/titulo/titulo.service';
import { Titulo } from 'app/shared/model/titulo.model';

describe('Component Tests', () => {
  describe('Titulo Management Update Component', () => {
    let comp: TituloUpdateComponent;
    let fixture: ComponentFixture<TituloUpdateComponent>;
    let service: TituloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [TituloUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TituloUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TituloUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TituloService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Titulo(123);
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
        const entity = new Titulo();
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
