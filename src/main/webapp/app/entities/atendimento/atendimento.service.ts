import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAtendimento } from 'app/shared/model/atendimento.model';

type EntityResponseType = HttpResponse<IAtendimento>;
type EntityArrayResponseType = HttpResponse<IAtendimento[]>;

@Injectable({ providedIn: 'root' })
export class AtendimentoService {
  public resourceUrl = SERVER_API_URL + 'api/atendimentos';

  constructor(protected http: HttpClient) {}

  create(atendimento: IAtendimento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(atendimento);
    return this.http
      .post<IAtendimento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(atendimento: IAtendimento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(atendimento);
    return this.http
      .put<IAtendimento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAtendimento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAtendimento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(atendimento: IAtendimento): IAtendimento {
    const copy: IAtendimento = Object.assign({}, atendimento, {
      dataDeChegada: atendimento.dataDeChegada != null && atendimento.dataDeChegada.isValid() ? atendimento.dataDeChegada.toJSON() : null,
      dataDeSaida: atendimento.dataDeSaida != null && atendimento.dataDeSaida.isValid() ? atendimento.dataDeSaida.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataDeChegada = res.body.dataDeChegada != null ? moment(res.body.dataDeChegada) : null;
      res.body.dataDeSaida = res.body.dataDeSaida != null ? moment(res.body.dataDeSaida) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((atendimento: IAtendimento) => {
        atendimento.dataDeChegada = atendimento.dataDeChegada != null ? moment(atendimento.dataDeChegada) : null;
        atendimento.dataDeSaida = atendimento.dataDeSaida != null ? moment(atendimento.dataDeSaida) : null;
      });
    }
    return res;
  }
}
