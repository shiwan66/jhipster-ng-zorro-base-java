import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { ModeloAtividadeDetailComponent } from 'app/entities/modelo-atividade/modelo-atividade-detail.component';
import { ModeloAtividade } from 'app/shared/model/modelo-atividade.model';

describe('Component Tests', () => {
  describe('ModeloAtividade Management Detail Component', () => {
    let comp: ModeloAtividadeDetailComponent;
    let fixture: ComponentFixture<ModeloAtividadeDetailComponent>;
    const route = ({ data: of({ modeloAtividade: new ModeloAtividade(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [ModeloAtividadeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ModeloAtividadeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ModeloAtividadeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load modeloAtividade on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.modeloAtividade).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
