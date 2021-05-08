import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITitulo } from 'app/shared/model/titulo.model';

type EntityResponseType = HttpResponse<ITitulo>;
type EntityArrayResponseType = HttpResponse<ITitulo[]>;

@Injectable({ providedIn: 'root' })
export class TituloService {
  public resourceUrl = SERVER_API_URL + 'api/titulos';

  constructor(protected http: HttpClient) {}

  create(titulo: ITitulo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(titulo);
    return this.http
      .post<ITitulo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(titulo: ITitulo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(titulo);
    return this.http
      .put<ITitulo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITitulo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITitulo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(titulo: ITitulo): ITitulo {
    const copy: ITitulo = Object.assign({}, titulo, {
      dataEmissao: titulo.dataEmissao != null && titulo.dataEmissao.isValid() ? titulo.dataEmissao.format(DATE_FORMAT) : null,
      dataPagamento: titulo.dataPagamento != null && titulo.dataPagamento.isValid() ? titulo.dataPagamento.format(DATE_FORMAT) : null,
      dataVencimento: titulo.dataVencimento != null && titulo.dataVencimento.isValid() ? titulo.dataVencimento.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataEmissao = res.body.dataEmissao != null ? moment(res.body.dataEmissao) : null;
      res.body.dataPagamento = res.body.dataPagamento != null ? moment(res.body.dataPagamento) : null;
      res.body.dataVencimento = res.body.dataVencimento != null ? moment(res.body.dataVencimento) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((titulo: ITitulo) => {
        titulo.dataEmissao = titulo.dataEmissao != null ? moment(titulo.dataEmissao) : null;
        titulo.dataPagamento = titulo.dataPagamento != null ? moment(titulo.dataPagamento) : null;
        titulo.dataVencimento = titulo.dataVencimento != null ? moment(titulo.dataVencimento) : null;
      });
    }
    return res;
  }
}
