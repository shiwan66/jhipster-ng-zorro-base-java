import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TituloService } from 'app/entities/titulo/titulo.service';
import { ITitulo, Titulo } from 'app/shared/model/titulo.model';
import { TipoTitulo } from 'app/shared/model/enumerations/tipo-titulo.model';

describe('Service Tests', () => {
  describe('Titulo Service', () => {
    let injector: TestBed;
    let service: TituloService;
    let httpMock: HttpTestingController;
    let elemDefault: ITitulo;
    let expectedResult: ITitulo | ITitulo[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TituloService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Titulo(0, false, TipoTitulo.RECEITA, 'AAAAAAA', 0, currentDate, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataEmissao: currentDate.format(DATE_FORMAT),
            dataPagamento: currentDate.format(DATE_FORMAT),
            dataVencimento: currentDate.format(DATE_FORMAT)
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

      it('should create a Titulo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataEmissao: currentDate.format(DATE_FORMAT),
            dataPagamento: currentDate.format(DATE_FORMAT),
            dataVencimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataEmissao: currentDate,
            dataPagamento: currentDate,
            dataVencimento: currentDate
          },
          returnedFromService
        );
        service
          .create(new Titulo())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Titulo', () => {
        const returnedFromService = Object.assign(
          {
            isPago: true,
            tipo: 'BBBBBB',
            descricao: 'BBBBBB',
            valor: 1,
            dataEmissao: currentDate.format(DATE_FORMAT),
            dataPagamento: currentDate.format(DATE_FORMAT),
            dataVencimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataEmissao: currentDate,
            dataPagamento: currentDate,
            dataVencimento: currentDate
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

      it('should return a list of Titulo', () => {
        const returnedFromService = Object.assign(
          {
            isPago: true,
            tipo: 'BBBBBB',
            descricao: 'BBBBBB',
            valor: 1,
            dataEmissao: currentDate.format(DATE_FORMAT),
            dataPagamento: currentDate.format(DATE_FORMAT),
            dataVencimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataEmissao: currentDate,
            dataPagamento: currentDate,
            dataVencimento: currentDate
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

      it('should delete a Titulo', () => {
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
