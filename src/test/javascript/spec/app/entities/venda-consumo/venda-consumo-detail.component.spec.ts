import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { VendaConsumoDetailComponent } from 'app/entities/venda-consumo/venda-consumo-detail.component';
import { VendaConsumo } from 'app/shared/model/venda-consumo.model';

describe('Component Tests', () => {
  describe('VendaConsumo Management Detail Component', () => {
    let comp: VendaConsumoDetailComponent;
    let fixture: ComponentFixture<VendaConsumoDetailComponent>;
    const route = ({ data: of({ vendaConsumo: new VendaConsumo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [VendaConsumoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VendaConsumoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VendaConsumoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vendaConsumo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vendaConsumo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
