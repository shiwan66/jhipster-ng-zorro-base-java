import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { ConsumoService } from 'app/entities/consumo/consumo.service';
import { IConsumo, Consumo } from 'app/shared/model/consumo.model';
import { TipoConsumo } from 'app/shared/model/enumerations/tipo-consumo.model';

describe('Service Tests', () => {
  describe('Consumo Service', () => {
    let injector: TestBed;
    let service: ConsumoService;
    let httpMock: HttpTestingController;
    let elemDefault: IConsumo;
    let expectedResult: IConsumo | IConsumo[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ConsumoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Consumo(0, 'AAAAAAA', TipoConsumo.PRODUTO, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Consumo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Consumo())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Consumo', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            tipo: 'BBBBBB',
            estoque: 1,
            valorUnitario: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Consumo', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            tipo: 'BBBBBB',
            estoque: 1,
            valorUnitario: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
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

      it('should delete a Consumo', () => {
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
