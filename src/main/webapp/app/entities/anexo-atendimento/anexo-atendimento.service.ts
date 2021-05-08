import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnexoAtendimento } from 'app/shared/model/anexo-atendimento.model';

type EntityResponseType = HttpResponse<IAnexoAtendimento>;
type EntityArrayResponseType = HttpResponse<IAnexoAtendimento[]>;

@Injectable({ providedIn: 'root' })
export class AnexoAtendimentoService {
  public resourceUrl = SERVER_API_URL + 'api/anexo-atendimentos';

  constructor(protected http: HttpClient) {}

  create(anexoAtendimento: IAnexoAtendimento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexoAtendimento);
    return this.http
      .post<IAnexoAtendimento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(anexoAtendimento: IAnexoAtendimento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexoAtendimento);
    return this.http
      .put<IAnexoAtendimento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnexoAtendimento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnexoAtendimento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(anexoAtendimento: IAnexoAtendimento): IAnexoAtendimento {
    const copy: IAnexoAtendimento = Object.assign({}, anexoAtendimento, {
      data: anexoAtendimento.data != null && anexoAtendimento.data.isValid() ? anexoAtendimento.data.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data != null ? moment(res.body.data) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((anexoAtendimento: IAnexoAtendimento) => {
        anexoAtendimento.data = anexoAtendimento.data != null ? moment(anexoAtendimento.data) : null;
      });
    }
    return res;
  }
}
