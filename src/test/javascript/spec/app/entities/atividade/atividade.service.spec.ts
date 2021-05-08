import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AtividadeService } from 'app/entities/atividade/atividade.service';
import { IAtividade, Atividade } from 'app/shared/model/atividade.model';

describe('Service Tests', () => {
  describe('Atividade Service', () => {
    let injector: TestBed;
    let service: AtividadeService;
    let httpMock: HttpTestingController;
    let elemDefault: IAtividade;
    let expectedResult: IAtividade | IAtividade[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AtividadeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Atividade(0, 'AAAAAAA', currentDate, currentDate, 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            inicio: currentDate.format(DATE_TIME_FORMAT),
            termino: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Atividade', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            inicio: currentDate.format(DATE_TIME_FORMAT),
            termino: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            inicio: currentDate,
            termino: currentDate
          },
          returnedFromService
        );
        service
          .create(new Atividade())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Atividade', () => {
        const returnedFromService = Object.assign(
          {
            titulo: 'BBBBBB',
            inicio: currentDate.format(DATE_TIME_FORMAT),
            termino: currentDate.format(DATE_TIME_FORMAT),
            observacao: 'BBBBBB',
            realizado: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inicio: currentDate,
            termino: currentDate
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

      it('should return a list of Atividade', () => {
        const returnedFromService = Object.assign(
          {
            titulo: 'BBBBBB',
            inicio: currentDate.format(DATE_TIME_FORMAT),
            termino: currentDate.format(DATE_TIME_FORMAT),
            observacao: 'BBBBBB',
            realizado: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            inicio: currentDate,
            termino: currentDate
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

      it('should delete a Atividade', () => {
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
