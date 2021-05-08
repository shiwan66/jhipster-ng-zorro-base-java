import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnimalTipoDeAlteracao } from 'app/shared/model/animal-tipo-de-alteracao.model';

type EntityResponseType = HttpResponse<IAnimalTipoDeAlteracao>;
type EntityArrayResponseType = HttpResponse<IAnimalTipoDeAlteracao[]>;

@Injectable({ providedIn: 'root' })
export class AnimalTipoDeAlteracaoService {
  public resourceUrl = SERVER_API_URL + 'api/animal-tipo-de-alteracaos';

  constructor(protected http: HttpClient) {}

  create(animalTipoDeAlteracao: IAnimalTipoDeAlteracao): Observable<EntityResponseType> {
    return this.http.post<IAnimalTipoDeAlteracao>(this.resourceUrl, animalTipoDeAlteracao, { observe: 'response' });
  }

  update(animalTipoDeAlteracao: IAnimalTipoDeAlteracao): Observable<EntityResponseType> {
    return this.http.put<IAnimalTipoDeAlteracao>(this.resourceUrl, animalTipoDeAlteracao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnimalTipoDeAlteracao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnimalTipoDeAlteracao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
