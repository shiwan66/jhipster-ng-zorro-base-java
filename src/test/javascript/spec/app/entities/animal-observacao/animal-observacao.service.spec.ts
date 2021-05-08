import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AnimalObservacaoService } from 'app/entities/animal-observacao/animal-observacao.service';
import { IAnimalObservacao, AnimalObservacao } from 'app/shared/model/animal-observacao.model';

describe('Service Tests', () => {
  describe('AnimalObservacao Service', () => {
    let injector: TestBed;
    let service: AnimalObservacaoService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnimalObservacao;
    let expectedResult: IAnimalObservacao | IAnimalObservacao[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AnimalObservacaoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AnimalObservacao(0, currentDate, 'AAAAAAA');
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

      it('should create a AnimalObservacao', () => {
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
          .create(new AnimalObservacao())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AnimalObservacao', () => {
        const returnedFromService = Object.assign(
          {
            dataAlteracao: currentDate.format(DATE_FORMAT),
            observacao: 'BBBBBB'
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

      it('should return a list of AnimalObservacao', () => {
        const returnedFromService = Object.assign(
          {
            dataAlteracao: currentDate.format(DATE_FORMAT),
            observacao: 'BBBBBB'
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

      it('should delete a AnimalObservacao', () => {
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
