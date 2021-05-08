import { Moment } from 'moment';

export interface IAnimalCio {
  id?: number;
  dataDoCio?: Moment;
  observacao?: any;
  animalId?: number;
}

export class AnimalCio implements IAnimalCio {
  constructor(public id?: number, public dataDoCio?: Moment, public observacao?: any, public animalId?: number) {}
}
