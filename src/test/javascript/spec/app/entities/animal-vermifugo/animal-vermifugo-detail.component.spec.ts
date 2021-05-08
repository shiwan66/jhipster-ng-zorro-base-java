import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NgzorroTestModule } from '../../../test.module';
import { AnimalVermifugoDetailComponent } from 'app/entities/animal-vermifugo/animal-vermifugo-detail.component';
import { AnimalVermifugo } from 'app/shared/model/animal-vermifugo.model';

describe('Component Tests', () => {
  describe('AnimalVermifugo Management Detail Component', () => {
    let comp: AnimalVermifugoDetailComponent;
    let fixture: ComponentFixture<AnimalVermifugoDetailComponent>;
    const route = ({ data: of({ animalVermifugo: new AnimalVermifugo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NgzorroTestModule],
        declarations: [AnimalVermifugoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnimalVermifugoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnimalVermifugoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load animalVermifugo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.animalVermifugo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
