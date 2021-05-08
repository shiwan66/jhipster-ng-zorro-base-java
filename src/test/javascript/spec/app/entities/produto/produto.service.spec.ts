import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ProdutoService } from 'app/entities/produto/produto.service';
import { IProduto, Produto } from 'app/shared/model/produto.model';

describe('Service Tests', () => {
  describe('Produto Service', () => {
    let injector: TestBed;
    let service: ProdutoService;
    let httpMock: HttpTestingController;
    let elemDefault: IProduto;
    let expectedResult: IProduto | IProduto[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProdutoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Produto(0, 'image/png', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            data: currentDate.format(DATE_FORMAT),
            hora: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Produto', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            data: currentDate.format(DATE_FORMAT),
            hora: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate,
            hora: currentDate
          },
          returnedFromService
        );
        service
          .create(new Produto())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Produto', () => {
        const returnedFromService = Object.assign(
          {
            imagem: 'BBBBBB',
            nome: 'BBBBBB',
            descricao: 'BBBBBB',
            preco: 1,
            data: currentDate.format(DATE_FORMAT),
            hora: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            data: currentDate,
            hora: currentDate
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

      it('should return a list of Produto', () => {
        const returnedFromService = Object.assign(
          {
            imagem: 'BBBBBB',
            nome: 'BBBBBB',
            descricao: 'BBBBBB',
            preco: 1,
            data: currentDate.format(DATE_FORMAT),
            hora: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate,
            hora: currentDate
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

      it('should delete a Produto', () => {
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
