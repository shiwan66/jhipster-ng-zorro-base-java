import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NgzorroTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { AnexoAtendimentoDeleteDialogComponent } from 'app/entities/anexo-atendimento/anexo-atendimento-delete-dialog.component';
import { AnexoAtendimentoService } from 'app/entities/anexo-atendimento/anexo-atendimento.service';

describe('Component Tests', () => {
  describe('AnexoAtendimento Management Delete Component', () => {
    let comp: AnexoAtendimentoDeleteDialogComponent;
    let fixture: ComponentFixture<AnexoAtendimentoDeleteDialogComponent>;
    let service: AnexoAtendimentoService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnexoAtendimentoDeleteDialogComponent]
      })
        .overrideTemplate(AnexoAtendimentoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnexoAtendimentoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnexoAtendimentoService);
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
