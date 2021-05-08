import { IVendaConsumo } from 'app/shared/model/venda-consumo.model';
import { IMovimentacaoDeEstoque } from 'app/shared/model/movimentacao-de-estoque.model';
import { TipoConsumo } from 'app/shared/model/enumerations/tipo-consumo.model';

export interface IConsumo {
  id?: number;
  nome?: string;
  tipo?: TipoConsumo;
  estoque?: number;
  valorUnitario?: number;
  vendaConsumos?: IVendaConsumo[];
  movimentacaoDeEstoques?: IMovimentacaoDeEstoque[];
}

export class Consumo implements IConsumo {
  constructor(
    public id?: number,
    public nome?: string,
    public tipo?: TipoConsumo,
    public estoque?: number,
    public valorUnitario?: number,
    public vendaConsumos?: IVendaConsumo[],
    public movimentacaoDeEstoques?: IMovimentacaoDeEstoque[]
  ) {}
}
