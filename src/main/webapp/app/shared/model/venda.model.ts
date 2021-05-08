import { Moment } from 'moment';
import { IVendaConsumo } from 'app/shared/model/venda-consumo.model';
import { TipoSituacaoDoLancamento } from 'app/shared/model/enumerations/tipo-situacao-do-lancamento.model';

export interface IVenda {
  id?: number;
  observacao?: any;
  dataDaCompra?: Moment;
  dataDoPagamento?: Moment;
  desconto?: number;
  situacao?: TipoSituacaoDoLancamento;
  valorTotal?: number;
  vendaConsumos?: IVendaConsumo[];
  atendimentoId?: number;
}

export class Venda implements IVenda {
  constructor(
    public id?: number,
    public observacao?: any,
    public dataDaCompra?: Moment,
    public dataDoPagamento?: Moment,
    public desconto?: number,
    public situacao?: TipoSituacaoDoLancamento,
    public valorTotal?: number,
    public vendaConsumos?: IVendaConsumo[],
    public atendimentoId?: number
  ) {}
}
