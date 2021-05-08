import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TutorService } from 'app/entities/tutor/tutor.service';
import { ITutor, Tutor } from 'app/shared/model/tutor.model';
import { Sexo } from 'app/shared/model/enumerations/sexo.model';

describe('Service Tests', () => {
  describe('Tutor Service', () => {
    let injector: TestBed;
    let service: TutorService;
    let httpMock: HttpTestingController;
    let elemDefault: ITutor;
    let expectedResult: ITutor | ITutor[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TutorService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Tutor(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        Sexo.MASCULINO,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataCadastro: currentDate.format(DATE_FORMAT)
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

      it('should create a Tutor', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataCadastro: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataCadastro: currentDate
          },
          returnedFromService
        );
        service
          .create(new Tutor())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Tutor', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            sobrenome: 'BBBBBB',
            telefone1: 'BBBBBB',
            telefone2: 'BBBBBB',
            email: 'BBBBBB',
            foto: 'BBBBBB',
            fotoUrl: 'BBBBBB',
            cpf: 'BBBBBB',
            sexo: 'BBBBBB',
            dataCadastro: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataCadastro: currentDate
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

      it('should return a list of Tutor', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            sobrenome: 'BBBBBB',
            telefone1: 'BBBBBB',
            telefone2: 'BBBBBB',
            email: 'BBBBBB',
            foto: 'BBBBBB',
            fotoUrl: 'BBBBBB',
            cpf: 'BBBBBB',
            sexo: 'BBBBBB',
            dataCadastro: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataCadastro: currentDate
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

      it('should delete a Tutor', () => {
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
