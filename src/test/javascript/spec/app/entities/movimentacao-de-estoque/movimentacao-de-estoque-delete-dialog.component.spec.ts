import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NgzorroTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { MovimentacaoDeEstoqueDeleteDialogComponent } from 'app/entities/movimentacao-de-estoque/movimentacao-de-estoque-delete-dialog.component';
import { MovimentacaoDeEstoqueService } from 'app/entities/movimentacao-de-estoque/movimentacao-de-estoque.service';

describe('Component Tests', () => {
  describe('MovimentacaoDeEstoque Management Delete Component', () => {
    let comp: MovimentacaoDeEstoqueDeleteDialogComponent;
    let fixture: ComponentFixture<MovimentacaoDeEstoqueDeleteDialogComponent>;
    let service: MovimentacaoDeEstoqueService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [MovimentacaoDeEstoqueDeleteDialogComponent]
      })
        .overrideTemplate(MovimentacaoDeEstoqueDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MovimentacaoDeEstoqueDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MovimentacaoDeEstoqueService);
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
