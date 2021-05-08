import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnimalCio } from 'app/shared/model/animal-cio.model';

type EntityResponseType = HttpResponse<IAnimalCio>;
type EntityArrayResponseType = HttpResponse<IAnimalCio[]>;

@Injectable({ providedIn: 'root' })
export class AnimalCioService {
  public resourceUrl = SERVER_API_URL + 'api/animal-cios';

  constructor(protected http: HttpClient) {}

  create(animalCio: IAnimalCio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(animalCio);
    return this.http
      .post<IAnimalCio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(animalCio: IAnimalCio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(animalCio);
    return this.http
      .put<IAnimalCio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnimalCio>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnimalCio[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(animalCio: IAnimalCio): IAnimalCio {
    const copy: IAnimalCio = Object.assign({}, animalCio, {
      dataDoCio: animalCio.dataDoCio != null && animalCio.dataDoCio.isValid() ? animalCio.dataDoCio.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataDoCio = res.body.dataDoCio != null ? moment(res.body.dataDoCio) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((animalCio: IAnimalCio) => {
        animalCio.dataDoCio = animalCio.dataDoCio != null ? moment(animalCio.dataDoCio) : null;
      });
    }
    return res;
  }
}
