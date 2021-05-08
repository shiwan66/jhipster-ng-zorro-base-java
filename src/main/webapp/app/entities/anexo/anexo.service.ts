import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnexo } from 'app/shared/model/anexo.model';

type EntityResponseType = HttpResponse<IAnexo>;
type EntityArrayResponseType = HttpResponse<IAnexo[]>;

@Injectable({ providedIn: 'root' })
export class AnexoService {
  public resourceUrl = SERVER_API_URL + 'api/anexos';

  constructor(protected http: HttpClient) {}

  create(anexo: IAnexo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexo);
    return this.http
      .post<IAnexo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(anexo: IAnexo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexo);
    return this.http
      .put<IAnexo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnexo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnexo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(anexo: IAnexo): IAnexo {
    const copy: IAnexo = Object.assign({}, anexo, {
      data: anexo.data != null && anexo.data.isValid() ? anexo.data.toJSON() : null
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
      res.body.forEach((anexo: IAnexo) => {
        anexo.data = anexo.data != null ? moment(anexo.data) : null;
      });
    }
    return res;
  }
}
