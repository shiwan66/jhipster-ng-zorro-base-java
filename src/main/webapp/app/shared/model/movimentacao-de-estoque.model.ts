import { Moment } from 'moment';
import { TipoMovimentacaoDeEstoque } from 'app/shared/model/enumerations/tipo-movimentacao-de-estoque.model';

export interface IMovimentacaoDeEstoque {
  id?: number;
  tipo?: TipoMovimentacaoDeEstoque;
  descricao?: any;
  data?: Moment;
  quantidade?: number;
  consumoId?: number;
}

export class MovimentacaoDeEstoque implements IMovimentacaoDeEstoque {
  constructor(
    public id?: number,
    public tipo?: TipoMovimentacaoDeEstoque,
    public descricao?: any,
    public data?: Moment,
    public quantidade?: number,
    public consumoId?: number
  ) {}
}
