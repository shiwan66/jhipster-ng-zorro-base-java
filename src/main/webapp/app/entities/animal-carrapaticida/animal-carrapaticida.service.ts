import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnimalCarrapaticida } from 'app/shared/model/animal-carrapaticida.model';

type EntityResponseType = HttpResponse<IAnimalCarrapaticida>;
type EntityArrayResponseType = HttpResponse<IAnimalCarrapaticida[]>;

@Injectable({ providedIn: 'root' })
export class AnimalCarrapaticidaService {
  public resourceUrl = SERVER_API_URL + 'api/animal-carrapaticidas';

  constructor(protected http: HttpClient) {}

  create(animalCarrapaticida: IAnimalCarrapaticida): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(animalCarrapaticida);
    return this.http
      .post<IAnimalCarrapaticida>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(animalCarrapaticida: IAnimalCarrapaticida): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(animalCarrapaticida);
    return this.http
      .put<IAnimalCarrapaticida>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnimalCarrapaticida>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnimalCarrapaticida[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(animalCarrapaticida: IAnimalCarrapaticida): IAnimalCarrapaticida {
    const copy: IAnimalCarrapaticida = Object.assign({}, animalCarrapaticida, {
      dataAplicacao:
        animalCarrapaticida.dataAplicacao != null && animalCarrapaticida.dataAplicacao.isValid()
          ? animalCarrapaticida.dataAplicacao.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataAplicacao = res.body.dataAplicacao != null ? moment(res.body.dataAplicacao) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((animalCarrapaticida: IAnimalCarrapaticida) => {
        animalCarrapaticida.dataAplicacao = animalCarrapaticida.dataAplicacao != null ? moment(animalCarrapaticida.dataAplicacao) : null;
      });
    }
    return res;
  }
}
