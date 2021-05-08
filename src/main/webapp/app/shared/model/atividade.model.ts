import { Moment } from 'moment';
import { IModeloAtividade } from 'app/shared/model/modelo-atividade.model';

export interface IAtividade {
  id?: number;
  titulo?: string;
  inicio?: Moment;
  termino?: Moment;
  observacao?: string;
  realizado?: boolean;
  atendimentoId?: number;
  modeloAtividades?: IModeloAtividade[];
}

export class Atividade implements IAtividade {
  constructor(
    public id?: number,
    public titulo?: string,
    public inicio?: Moment,
    public termino?: Moment,
    public observacao?: string,
    public realizado?: boolean,
    public atendimentoId?: number,
    public modeloAtividades?: IModeloAtividade[]
  ) {
    this.realizado = this.realizado || false;
  }
}
