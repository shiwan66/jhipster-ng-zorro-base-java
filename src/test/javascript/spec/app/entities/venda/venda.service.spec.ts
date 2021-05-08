import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { VendaService } from 'app/entities/venda/venda.service';
import { IVenda, Venda } from 'app/shared/model/venda.model';
import { TipoSituacaoDoLancamento } from 'app/shared/model/enumerations/tipo-situacao-do-lancamento.model';

describe('Service Tests', () => {
  describe('Venda Service', () => {
    let injector: TestBed;
    let service: VendaService;
    let httpMock: HttpTestingController;
    let elemDefault: IVenda;
    let expectedResult: IVenda | IVenda[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(VendaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Venda(0, 'AAAAAAA', currentDate, currentDate, 0, TipoSituacaoDoLancamento.PAGO, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataDaCompra: currentDate.format(DATE_TIME_FORMAT),
            dataDoPagamento: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Venda', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataDaCompra: currentDate.format(DATE_TIME_FORMAT),
            dataDoPagamento: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataDaCompra: currentDate,
            dataDoPagamento: currentDate
          },
          returnedFromService
        );
        service
          .create(new Venda())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Venda', () => {
        const returnedFromService = Object.assign(
          {
            observacao: 'BBBBBB',
            dataDaCompra: currentDate.format(DATE_TIME_FORMAT),
            dataDoPagamento: currentDate.format(DATE_TIME_FORMAT),
            desconto: 1,
            situacao: 'BBBBBB',
            valorTotal: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataDaCompra: currentDate,
            dataDoPagamento: currentDate
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

      it('should return a list of Venda', () => {
        const returnedFromService = Object.assign(
          {
            observacao: 'BBBBBB',
            dataDaCompra: currentDate.format(DATE_TIME_FORMAT),
            dataDoPagamento: currentDate.format(DATE_TIME_FORMAT),
            desconto: 1,
            situacao: 'BBBBBB',
            valorTotal: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataDaCompra: currentDate,
            dataDoPagamento: currentDate
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

      it('should delete a Venda', () => {
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
