import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAtividade } from 'app/shared/model/atividade.model';

type EntityResponseType = HttpResponse<IAtividade>;
type EntityArrayResponseType = HttpResponse<IAtividade[]>;

@Injectable({ providedIn: 'root' })
export class AtividadeService {
  public resourceUrl = SERVER_API_URL + 'api/atividades';

  constructor(protected http: HttpClient) {}

  create(atividade: IAtividade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(atividade);
    return this.http
      .post<IAtividade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(atividade: IAtividade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(atividade);
    return this.http
      .put<IAtividade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAtividade>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAtividade[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(atividade: IAtividade): IAtividade {
    const copy: IAtividade = Object.assign({}, atividade, {
      inicio: atividade.inicio != null && atividade.inicio.isValid() ? atividade.inicio.toJSON() : null,
      termino: atividade.termino != null && atividade.termino.isValid() ? atividade.termino.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inicio = res.body.inicio != null ? moment(res.body.inicio) : null;
      res.body.termino = res.body.termino != null ? moment(res.body.termino) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((atividade: IAtividade) => {
        atividade.inicio = atividade.inicio != null ? moment(atividade.inicio) : null;
        atividade.termino = atividade.termino != null ? moment(atividade.termino) : null;
      });
    }
    return res;
  }
}
