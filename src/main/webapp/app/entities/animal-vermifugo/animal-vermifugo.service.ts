import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnimalVermifugo } from 'app/shared/model/animal-vermifugo.model';

type EntityResponseType = HttpResponse<IAnimalVermifugo>;
type EntityArrayResponseType = HttpResponse<IAnimalVermifugo[]>;

@Injectable({ providedIn: 'root' })
export class AnimalVermifugoService {
  public resourceUrl = SERVER_API_URL + 'api/animal-vermifugos';

  constructor(protected http: HttpClient) {}

  create(animalVermifugo: IAnimalVermifugo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(animalVermifugo);
    return this.http
      .post<IAnimalVermifugo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(animalVermifugo: IAnimalVermifugo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(animalVermifugo);
    return this.http
      .put<IAnimalVermifugo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnimalVermifugo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnimalVermifugo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(animalVermifugo: IAnimalVermifugo): IAnimalVermifugo {
    const copy: IAnimalVermifugo = Object.assign({}, animalVermifugo, {
      dataDaAplicacao:
        animalVermifugo.dataDaAplicacao != null && animalVermifugo.dataDaAplicacao.isValid()
          ? animalVermifugo.dataDaAplicacao.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataDaAplicacao = res.body.dataDaAplicacao != null ? moment(res.body.dataDaAplicacao) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((animalVermifugo: IAnimalVermifugo) => {
        animalVermifugo.dataDaAplicacao = animalVermifugo.dataDaAplicacao != null ? moment(animalVermifugo.dataDaAplicacao) : null;
      });
    }
    return res;
  }
}
