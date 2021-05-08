export interface IAnimalTipoDeVacina {
  id?: number;
  descricao?: string;
}

export class AnimalTipoDeVacina implements IAnimalTipoDeVacina {
  constructor(public id?: number, public descricao?: string) {}
}
