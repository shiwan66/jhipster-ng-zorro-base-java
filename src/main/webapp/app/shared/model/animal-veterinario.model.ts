export interface IAnimalVeterinario {
  id?: number;
  nome?: string;
  telefone?: string;
  clinica?: string;
}

export class AnimalVeterinario implements IAnimalVeterinario {
  constructor(public id?: number, public nome?: string, public telefone?: string, public clinica?: string) {}
}
