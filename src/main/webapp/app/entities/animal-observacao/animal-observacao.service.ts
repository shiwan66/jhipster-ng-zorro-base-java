import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnimalObservacao } from 'app/shared/model/animal-observacao.model';

type EntityResponseType = HttpResponse<IAnimalObservacao>;
type EntityArrayResponseType = HttpResponse<IAnimalObservacao[]>;

@Injectable({ providedIn: 'root' })
export class AnimalObservacaoService {
  public resourceUrl = SERVER_API_URL + 'api/animal-observacaos';

  constructor(protected http: HttpClient) {}

  create(animalObservacao: IAnimalObservacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(animalObservacao);
    return this.http
      .post<IAnimalObservacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(animalObservacao: IAnimalObservacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(animalObservacao);
    return this.http
      .put<IAnimalObservacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnimalObservacao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnimalObservacao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(animalObservacao: IAnimalObservacao): IAnimalObservacao {
    const copy: IAnimalObservacao = Object.assign({}, animalObservacao, {
      dataAlteracao:
        animalObservacao.dataAlteracao != null && animalObservacao.dataAlteracao.isValid()
          ? animalObservacao.dataAlteracao.format(DATE_FORMAT)
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
      res.body.forEach((animalObservacao: IAnimalObservacao) => {
        animalObservacao.dataAlteracao = animalObservacao.dataAlteracao != null ? moment(animalObservacao.dataAlteracao) : null;
      });
    }
    return res;
  }
}
