import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRaca } from 'app/shared/model/raca.model';

type EntityResponseType = HttpResponse<IRaca>;
type EntityArrayResponseType = HttpResponse<IRaca[]>;

@Injectable({ providedIn: 'root' })
export class RacaService {
  public resourceUrl = SERVER_API_URL + 'api/racas';

  constructor(protected http: HttpClient) {}

  create(raca: IRaca): Observable<EntityResponseType> {
    return this.http.post<IRaca>(this.resourceUrl, raca, { observe: 'response' });
  }

  update(raca: IRaca): Observable<EntityResponseType> {
    return this.http.put<IRaca>(this.resourceUrl, raca, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRaca>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRaca[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
