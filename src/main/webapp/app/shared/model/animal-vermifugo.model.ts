import { Moment } from 'moment';

export interface IAnimalVermifugo {
  id?: number;
  nome?: string;
  dataDaAplicacao?: Moment;
  animalId?: number;
}

export class AnimalVermifugo implements IAnimalVermifugo {
  constructor(public id?: number, public nome?: string, public dataDaAplicacao?: Moment, public animalId?: number) {}
}
