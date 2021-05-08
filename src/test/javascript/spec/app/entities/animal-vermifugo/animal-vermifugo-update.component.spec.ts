import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalVermifugoUpdateComponent } from 'app/entities/animal-vermifugo/animal-vermifugo-update.component';
import { AnimalVermifugoService } from 'app/entities/animal-vermifugo/animal-vermifugo.service';
import { AnimalVermifugo } from 'app/shared/model/animal-vermifugo.model';

describe('Component Tests', () => {
  describe('AnimalVermifugo Management Update Component', () => {
    let comp: AnimalVermifugoUpdateComponent;
    let fixture: ComponentFixture<AnimalVermifugoUpdateComponent>;
    let service: AnimalVermifugoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalVermifugoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnimalVermifugoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnimalVermifugoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnimalVermifugoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnimalVermifugo(123);
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
        const entity = new AnimalVermifugo();
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
