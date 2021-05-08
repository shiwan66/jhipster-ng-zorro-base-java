import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AnimalCarrapaticidaService } from 'app/entities/animal-carrapaticida/animal-carrapaticida.service';
import { IAnimalCarrapaticida, AnimalCarrapaticida } from 'app/shared/model/animal-carrapaticida.model';

describe('Service Tests', () => {
  describe('AnimalCarrapaticida Service', () => {
    let injector: TestBed;
    let service: AnimalCarrapaticidaService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnimalCarrapaticida;
    let expectedResult: IAnimalCarrapaticida | IAnimalCarrapaticida[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AnimalCarrapaticidaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AnimalCarrapaticida(0, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataAplicacao: currentDate.format(DATE_FORMAT)
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

      it('should create a AnimalCarrapaticida', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataAplicacao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataAplicacao: currentDate
          },
          returnedFromService
        );
        service
          .create(new AnimalCarrapaticida())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AnimalCarrapaticida', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            dataAplicacao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataAplicacao: currentDate
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

      it('should return a list of AnimalCarrapaticida', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            dataAplicacao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataAplicacao: currentDate
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

      it('should delete a AnimalCarrapaticida', () => {
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
