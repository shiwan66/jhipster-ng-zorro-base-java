import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NgzorroTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { AnimalCarrapaticidaDeleteDialogComponent } from 'app/entities/animal-carrapaticida/animal-carrapaticida-delete-dialog.component';
import { AnimalCarrapaticidaService } from 'app/entities/animal-carrapaticida/animal-carrapaticida.service';

describe('Component Tests', () => {
  describe('AnimalCarrapaticida Management Delete Component', () => {
    let comp: AnimalCarrapaticidaDeleteDialogComponent;
    let fixture: ComponentFixture<AnimalCarrapaticidaDeleteDialogComponent>;
    let service: AnimalCarrapaticidaService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalCarrapaticidaDeleteDialogComponent]
      })
        .overrideTemplate(AnimalCarrapaticidaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnimalCarrapaticidaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnimalCarrapaticidaService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.clear();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
