import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnimalVeterinario } from 'app/shared/model/animal-veterinario.model';

type EntityResponseType = HttpResponse<IAnimalVeterinario>;
type EntityArrayResponseType = HttpResponse<IAnimalVeterinario[]>;

@Injectable({ providedIn: 'root' })
export class AnimalVeterinarioService {
  public resourceUrl = SERVER_API_URL + 'api/animal-veterinarios';

  constructor(protected http: HttpClient) {}

  create(animalVeterinario: IAnimalVeterinario): Observable<EntityResponseType> {
    return this.http.post<IAnimalVeterinario>(this.resourceUrl, animalVeterinario, { observe: 'response' });
  }

  update(animalVeterinario: IAnimalVeterinario): Observable<EntityResponseType> {
    return this.http.put<IAnimalVeterinario>(this.resourceUrl, animalVeterinario, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnimalVeterinario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnimalVeterinario[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
