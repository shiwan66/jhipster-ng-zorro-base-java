import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProduto } from 'app/shared/model/produto.model';

type EntityResponseType = HttpResponse<IProduto>;
type EntityArrayResponseType = HttpResponse<IProduto[]>;

@Injectable({ providedIn: 'root' })
export class ProdutoService {
  public resourceUrl = SERVER_API_URL + 'api/produtos';
  eventConfirmDelete: EventEmitter<string> = new EventEmitter();

  constructor(protected http: HttpClient) {}

  create(produto: IProduto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(produto);
    return this.http
      .post<IProduto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(produto: IProduto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(produto);
    return this.http
      .put<IProduto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProduto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProduto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  deleteMultiple(ids: number[]): Observable<any> {
    return this.http.post(`${this.resourceUrl}/delete`, ids);
  }

  protected convertDateFromClient(produto: IProduto): IProduto {
    const copy: IProduto = Object.assign({}, produto, {
      data: produto.data != null && produto.data.isValid() ? produto.data.format(DATE_FORMAT) : null,
      hora: produto.hora != null && produto.hora.isValid() ? produto.hora.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data != null ? moment(res.body.data) : null;
      res.body.hora = res.body.hora != null ? moment(res.body.hora) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((produto: IProduto) => {
        produto.data = produto.data != null ? moment(produto.data) : null;
        produto.hora = produto.hora != null ? moment(produto.hora) : null;
      });
    }
    return res;
  }

  emitEventConfirmDelete() {
    this.eventConfirmDelete.emit();
  }
}
