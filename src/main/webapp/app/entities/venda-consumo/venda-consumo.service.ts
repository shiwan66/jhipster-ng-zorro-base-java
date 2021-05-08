import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVendaConsumo } from 'app/shared/model/venda-consumo.model';

type EntityResponseType = HttpResponse<IVendaConsumo>;
type EntityArrayResponseType = HttpResponse<IVendaConsumo[]>;

@Injectable({ providedIn: 'root' })
export class VendaConsumoService {
  public resourceUrl = SERVER_API_URL + 'api/venda-consumos';

  constructor(protected http: HttpClient) {}

  create(vendaConsumo: IVendaConsumo): Observable<EntityResponseType> {
    return this.http.post<IVendaConsumo>(this.resourceUrl, vendaConsumo, { observe: 'response' });
  }

  update(vendaConsumo: IVendaConsumo): Observable<EntityResponseType> {
    return this.http.put<IVendaConsumo>(this.resourceUrl, vendaConsumo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVendaConsumo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVendaConsumo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
