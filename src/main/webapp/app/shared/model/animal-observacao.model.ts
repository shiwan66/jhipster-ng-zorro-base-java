import { Moment } from 'moment';

export interface IAnimalObservacao {
  id?: number;
  dataAlteracao?: Moment;
  observacao?: any;
  animalId?: number;
}

export class AnimalObservacao implements IAnimalObservacao {
  constructor(public id?: number, public dataAlteracao?: Moment, public observacao?: any, public animalId?: number) {}
}
