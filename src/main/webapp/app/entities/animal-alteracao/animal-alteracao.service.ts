import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnimalAlteracao } from 'app/shared/model/animal-alteracao.model';

type EntityResponseType = HttpResponse<IAnimalAlteracao>;
type EntityArrayResponseType = HttpResponse<IAnimalAlteracao[]>;

@Injectable({ providedIn: 'root' })
export class AnimalAlteracaoService {
  public resourceUrl = SERVER_API_URL + 'api/animal-alteracaos';

  constructor(protected http: HttpClient) {}

  create(animalAlteracao: IAnimalAlteracao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(animalAlteracao);
    return this.http
      .post<IAnimalAlteracao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(animalAlteracao: IAnimalAlteracao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(animalAlteracao);
    return this.http
      .put<IAnimalAlteracao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnimalAlteracao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnimalAlteracao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(animalAlteracao: IAnimalAlteracao): IAnimalAlteracao {
    const copy: IAnimalAlteracao = Object.assign({}, animalAlteracao, {
      dataAlteracao:
        animalAlteracao.dataAlteracao != null && animalAlteracao.dataAlteracao.isValid()
          ? animalAlteracao.dataAlteracao.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataAlteracao = res.body.dataAlteracao != null ? moment(res.body.dataAlteracao) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((animalAlteracao: IAnimalAlteracao) => {
        animalAlteracao.dataAlteracao = animalAlteracao.dataAlteracao != null ? moment(animalAlteracao.dataAlteracao) : null;
      });
    }
    return res;
  }
}
