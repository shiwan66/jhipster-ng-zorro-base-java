import { Moment } from 'moment';

export interface IAnimalCarrapaticida {
  id?: number;
  nome?: string;
  dataAplicacao?: Moment;
  animalId?: number;
}

export class AnimalCarrapaticida implements IAnimalCarrapaticida {
  constructor(public id?: number, public nome?: string, public dataAplicacao?: Moment, public animalId?: number) {}
}
