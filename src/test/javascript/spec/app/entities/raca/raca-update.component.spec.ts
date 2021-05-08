import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { RacaUpdateComponent } from 'app/entities/raca/raca-update.component';
import { RacaService } from 'app/entities/raca/raca.service';
import { Raca } from 'app/shared/model/raca.model';

describe('Component Tests', () => {
  describe('Raca Management Update Component', () => {
    let comp: RacaUpdateComponent;
    let fixture: ComponentFixture<RacaUpdateComponent>;
    let service: RacaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [RacaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RacaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RacaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RacaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Raca(123);
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
        const entity = new Raca();
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
