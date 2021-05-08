import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITutor } from 'app/shared/model/tutor.model';

type EntityResponseType = HttpResponse<ITutor>;
type EntityArrayResponseType = HttpResponse<ITutor[]>;

@Injectable({ providedIn: 'root' })
export class TutorService {
  public resourceUrl = SERVER_API_URL + 'api/tutors';

  constructor(protected http: HttpClient) {}

  create(tutor: ITutor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tutor);
    return this.http
      .post<ITutor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tutor: ITutor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tutor);
    return this.http
      .put<ITutor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITutor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITutor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tutor: ITutor): ITutor {
    const copy: ITutor = Object.assign({}, tutor, {
      dataCadastro: tutor.dataCadastro != null && tutor.dataCadastro.isValid() ? tutor.dataCadastro.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataCadastro = res.body.dataCadastro != null ? moment(res.body.dataCadastro) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tutor: ITutor) => {
        tutor.dataCadastro = tutor.dataCadastro != null ? moment(tutor.dataCadastro) : null;
      });
    }
    return res;
  }
}
