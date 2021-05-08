import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AnimalService } from 'app/entities/animal/animal.service';
import { IAnimal, Animal } from 'app/shared/model/animal.model';
import { AnimalSexo } from 'app/shared/model/enumerations/animal-sexo.model';

describe('Service Tests', () => {
  describe('Animal Service', () => {
    let injector: TestBed;
    let service: AnimalService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnimal;
    let expectedResult: IAnimal | IAnimal[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AnimalService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Animal(0, 'image/png', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', AnimalSexo.MACHO, 'AAAAAAA', 'AAAAAAA', false, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataNascimento: currentDate.format(DATE_FORMAT)
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

      it('should create a Animal', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataNascimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataNascimento: currentDate
          },
          returnedFromService
        );
        service
          .create(new Animal())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Animal', () => {
        const returnedFromService = Object.assign(
          {
            foto: 'BBBBBB',
            fotoUrl: 'BBBBBB',
            nome: 'BBBBBB',
            sexo: 'BBBBBB',
            pelagem: 'BBBBBB',
            temperamento: 'BBBBBB',
            emAtendimento: true,
            dataNascimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataNascimento: currentDate
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

      it('should return a list of Animal', () => {
        const returnedFromService = Object.assign(
          {
            foto: 'BBBBBB',
            fotoUrl: 'BBBBBB',
            nome: 'BBBBBB',
            sexo: 'BBBBBB',
            pelagem: 'BBBBBB',
            temperamento: 'BBBBBB',
            emAtendimento: true,
            dataNascimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataNascimento: currentDate
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

      it('should delete a Animal', () => {
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
