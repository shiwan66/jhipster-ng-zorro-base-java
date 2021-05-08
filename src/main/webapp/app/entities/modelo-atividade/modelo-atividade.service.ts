import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IModeloAtividade } from 'app/shared/model/modelo-atividade.model';

type EntityResponseType = HttpResponse<IModeloAtividade>;
type EntityArrayResponseType = HttpResponse<IModeloAtividade[]>;

@Injectable({ providedIn: 'root' })
export class ModeloAtividadeService {
  public resourceUrl = SERVER_API_URL + 'api/modelo-atividades';

  constructor(protected http: HttpClient) {}

  create(modeloAtividade: IModeloAtividade): Observable<EntityResponseType> {
    return this.http.post<IModeloAtividade>(this.resourceUrl, modeloAtividade, { observe: 'response' });
  }

  update(modeloAtividade: IModeloAtividade): Observable<EntityResponseType> {
    return this.http.put<IModeloAtividade>(this.resourceUrl, modeloAtividade, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IModeloAtividade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModeloAtividade[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
