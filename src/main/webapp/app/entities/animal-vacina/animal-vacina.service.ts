import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnimalVacina } from 'app/shared/model/animal-vacina.model';

type EntityResponseType = HttpResponse<IAnimalVacina>;
type EntityArrayResponseType = HttpResponse<IAnimalVacina[]>;

@Injectable({ providedIn: 'root' })
export class AnimalVacinaService {
  public resourceUrl = SERVER_API_URL + 'api/animal-vacinas';

  constructor(protected http: HttpClient) {}

  create(animalVacina: IAnimalVacina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(animalVacina);
    return this.http
      .post<IAnimalVacina>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(animalVacina: IAnimalVacina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(animalVacina);
    return this.http
      .put<IAnimalVacina>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnimalVacina>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnimalVacina[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(animalVacina: IAnimalVacina): IAnimalVacina {
    const copy: IAnimalVacina = Object.assign({}, animalVacina, {
      dataDaAplicacao:
        animalVacina.dataDaAplicacao != null && animalVacina.dataDaAplicacao.isValid()
          ? animalVacina.dataDaAplicacao.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataDaAplicacao = res.body.dataDaAplicacao != null ? moment(res.body.dataDaAplicacao) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((animalVacina: IAnimalVacina) => {
        animalVacina.dataDaAplicacao = animalVacina.dataDaAplicacao != null ? moment(animalVacina.dataDaAplicacao) : null;
      });
    }
    return res;
  }
}
