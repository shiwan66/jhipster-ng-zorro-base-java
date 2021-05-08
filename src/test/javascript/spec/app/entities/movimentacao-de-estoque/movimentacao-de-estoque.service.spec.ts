import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MovimentacaoDeEstoqueService } from 'app/entities/movimentacao-de-estoque/movimentacao-de-estoque.service';
import { IMovimentacaoDeEstoque, MovimentacaoDeEstoque } from 'app/shared/model/movimentacao-de-estoque.model';
import { TipoMovimentacaoDeEstoque } from 'app/shared/model/enumerations/tipo-movimentacao-de-estoque.model';

describe('Service Tests', () => {
  describe('MovimentacaoDeEstoque Service', () => {
    let injector: TestBed;
    let service: MovimentacaoDeEstoqueService;
    let httpMock: HttpTestingController;
    let elemDefault: IMovimentacaoDeEstoque;
    let expectedResult: IMovimentacaoDeEstoque | IMovimentacaoDeEstoque[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MovimentacaoDeEstoqueService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MovimentacaoDeEstoque(0, TipoMovimentacaoDeEstoque.ENTRADA, 'AAAAAAA', currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            data: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MovimentacaoDeEstoque', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            data: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );
        service
          .create(new MovimentacaoDeEstoque())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MovimentacaoDeEstoque', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            descricao: 'BBBBBB',
            data: currentDate.format(DATE_TIME_FORMAT),
            quantidade: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MovimentacaoDeEstoque', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            descricao: 'BBBBBB',
            data: currentDate.format(DATE_TIME_FORMAT),
            quantidade: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MovimentacaoDeEstoque', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
