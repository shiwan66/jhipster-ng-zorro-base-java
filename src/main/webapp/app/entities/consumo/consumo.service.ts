import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConsumo } from 'app/shared/model/consumo.model';

type EntityResponseType = HttpResponse<IConsumo>;
type EntityArrayResponseType = HttpResponse<IConsumo[]>;

@Injectable({ providedIn: 'root' })
export class ConsumoService {
  public resourceUrl = SERVER_API_URL + 'api/consumos';

  constructor(protected http: HttpClient) {}

  create(consumo: IConsumo): Observable<EntityResponseType> {
    return this.http.post<IConsumo>(this.resourceUrl, consumo, { observe: 'response' });
  }

  update(consumo: IConsumo): Observable<EntityResponseType> {
    return this.http.put<IConsumo>(this.resourceUrl, consumo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConsumo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConsumo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
