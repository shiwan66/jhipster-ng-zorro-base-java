import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { VendaConsumoUpdateComponent } from 'app/entities/venda-consumo/venda-consumo-update.component';
import { VendaConsumoService } from 'app/entities/venda-consumo/venda-consumo.service';
import { VendaConsumo } from 'app/shared/model/venda-consumo.model';

describe('Component Tests', () => {
  describe('VendaConsumo Management Update Component', () => {
    let comp: VendaConsumoUpdateComponent;
    let fixture: ComponentFixture<VendaConsumoUpdateComponent>;
    let service: VendaConsumoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [VendaConsumoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VendaConsumoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VendaConsumoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VendaConsumoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VendaConsumo(123);
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
        const entity = new VendaConsumo();
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
