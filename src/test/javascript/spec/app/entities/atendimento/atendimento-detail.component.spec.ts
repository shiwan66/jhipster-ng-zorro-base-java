import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AtendimentoDetailComponent } from 'app/entities/atendimento/atendimento-detail.component';
import { Atendimento } from 'app/shared/model/atendimento.model';

describe('Component Tests', () => {
  describe('Atendimento Management Detail Component', () => {
    let comp: AtendimentoDetailComponent;
    let fixture: ComponentFixture<AtendimentoDetailComponent>;
    const route = ({ data: of({ atendimento: new Atendimento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AtendimentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AtendimentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AtendimentoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load atendimento on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.atendimento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
