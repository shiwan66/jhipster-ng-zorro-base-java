import { Moment } from 'moment';

export interface IAnimalVacina {
  id?: number;
  dataDaAplicacao?: Moment;
  animalTipoDeVacinaDescricao?: string;
  animalTipoDeVacinaId?: number;
  animalId?: number;
}

export class AnimalVacina implements IAnimalVacina {
  constructor(
    public id?: number,
    public dataDaAplicacao?: Moment,
    public animalTipoDeVacinaDescricao?: string,
    public animalTipoDeVacinaId?: number,
    public animalId?: number
  ) {}
}
