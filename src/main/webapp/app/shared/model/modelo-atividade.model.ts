import { IAtividade } from 'app/shared/model/atividade.model';

export interface IModeloAtividade {
  id?: number;
  descricao?: string;
  atividades?: IAtividade[];
}

export class ModeloAtividade implements IModeloAtividade {
  constructor(public id?: number, public descricao?: string, public atividades?: IAtividade[]) {}
}
