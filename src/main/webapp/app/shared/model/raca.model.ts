export interface IRaca {
  id?: number;
  nome?: string;
}

export class Raca implements IRaca {
  constructor(public id?: number, public nome?: string) {}
}
