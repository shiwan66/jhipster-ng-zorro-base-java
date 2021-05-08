export interface IAnimalTipoDeAlteracao {
  id?: number;
  descricao?: string;
}

export class AnimalTipoDeAlteracao implements IAnimalTipoDeAlteracao {
  constructor(public id?: number, public descricao?: string) {}
}
