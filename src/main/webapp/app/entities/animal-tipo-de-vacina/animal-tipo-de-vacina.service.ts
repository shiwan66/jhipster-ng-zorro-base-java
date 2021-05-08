import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnimalTipoDeVacina } from 'app/shared/model/animal-tipo-de-vacina.model';

type EntityResponseType = HttpResponse<IAnimalTipoDeVacina>;
type EntityArrayResponseType = HttpResponse<IAnimalTipoDeVacina[]>;

@Injectable({ providedIn: 'root' })
export class AnimalTipoDeVacinaService {
  public resourceUrl = SERVER_API_URL + 'api/animal-tipo-de-vacinas';

  constructor(protected http: HttpClient) {}

  create(animalTipoDeVacina: IAnimalTipoDeVacina): Observable<EntityResponseType> {
    return this.http.post<IAnimalTipoDeVacina>(this.resourceUrl, animalTipoDeVacina, { observe: 'response' });
  }

  update(animalTipoDeVacina: IAnimalTipoDeVacina): Observable<EntityResponseType> {
    return this.http.put<IAnimalTipoDeVacina>(this.resourceUrl, animalTipoDeVacina, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnimalTipoDeVacina>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnimalTipoDeVacina[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
