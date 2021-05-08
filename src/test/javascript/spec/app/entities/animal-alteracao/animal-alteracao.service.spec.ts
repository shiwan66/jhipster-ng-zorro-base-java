import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AnimalAlteracaoService } from 'app/entities/animal-alteracao/animal-alteracao.service';
import { IAnimalAlteracao, AnimalAlteracao } from 'app/shared/model/animal-alteracao.model';

describe('Service Tests', () => {
  describe('AnimalAlteracao Service', () => {
    let injector: TestBed;
    let service: AnimalAlteracaoService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnimalAlteracao;
    let expectedResult: IAnimalAlteracao | IAnimalAlteracao[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AnimalAlteracaoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AnimalAlteracao(0, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataAlteracao: currentDate.format(DATE_FORMAT)
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

      it('should create a AnimalAlteracao', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataAlteracao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataAlteracao: currentDate
          },
          returnedFromService
        );
        service
          .create(new AnimalAlteracao())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AnimalAlteracao', () => {
        const returnedFromService = Object.assign(
          {
            descricao: 'BBBBBB',
            dataAlteracao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataAlteracao: currentDate
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

      it('should return a list of AnimalAlteracao', () => {
        const returnedFromService = Object.assign(
          {
            descricao: 'BBBBBB',
            dataAlteracao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataAlteracao: currentDate
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

      it('should delete a AnimalAlteracao', () => {
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
