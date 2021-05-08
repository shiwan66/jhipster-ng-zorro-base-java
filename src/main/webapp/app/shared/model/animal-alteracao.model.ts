import { Moment } from 'moment';

export interface IAnimalAlteracao {
  id?: number;
  descricao?: any;
  dataAlteracao?: Moment;
  animalTipoDeAlteracaoDescricao?: string;
  animalTipoDeAlteracaoId?: number;
  animalId?: number;
}

export class AnimalAlteracao implements IAnimalAlteracao {
  constructor(
    public id?: number,
    public descricao?: any,
    public dataAlteracao?: Moment,
    public animalTipoDeAlteracaoDescricao?: string,
    public animalTipoDeAlteracaoId?: number,
    public animalId?: number
  ) {}
}
