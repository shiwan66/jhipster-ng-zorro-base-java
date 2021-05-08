import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AtendimentoService } from 'app/entities/atendimento/atendimento.service';
import { IAtendimento, Atendimento } from 'app/shared/model/atendimento.model';
import { AtendimentoSituacao } from 'app/shared/model/enumerations/atendimento-situacao.model';

describe('Service Tests', () => {
  describe('Atendimento Service', () => {
    let injector: TestBed;
    let service: AtendimentoService;
    let httpMock: HttpTestingController;
    let elemDefault: IAtendimento;
    let expectedResult: IAtendimento | IAtendimento[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AtendimentoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Atendimento(0, AtendimentoSituacao.EM_ANDAMENTO, currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataDeChegada: currentDate.format(DATE_TIME_FORMAT),
            dataDeSaida: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Atendimento', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataDeChegada: currentDate.format(DATE_TIME_FORMAT),
            dataDeSaida: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataDeChegada: currentDate,
            dataDeSaida: currentDate
          },
          returnedFromService
        );
        service
          .create(new Atendimento())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Atendimento', () => {
        const returnedFromService = Object.assign(
          {
            situacao: 'BBBBBB',
            dataDeChegada: currentDate.format(DATE_TIME_FORMAT),
            dataDeSaida: currentDate.format(DATE_TIME_FORMAT),
            observacao: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataDeChegada: currentDate,
            dataDeSaida: currentDate
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

      it('should return a list of Atendimento', () => {
        const returnedFromService = Object.assign(
          {
            situacao: 'BBBBBB',
            dataDeChegada: currentDate.format(DATE_TIME_FORMAT),
            dataDeSaida: currentDate.format(DATE_TIME_FORMAT),
            observacao: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataDeChegada: currentDate,
            dataDeSaida: currentDate
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

      it('should delete a Atendimento', () => {
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
