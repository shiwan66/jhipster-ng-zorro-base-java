import { Moment } from 'moment';
import { IAtividade } from 'app/shared/model/atividade.model';
import { IVenda } from 'app/shared/model/venda.model';
import { IAnexoAtendimento } from 'app/shared/model/anexo-atendimento.model';
import { AtendimentoSituacao } from 'app/shared/model/enumerations/atendimento-situacao.model';

export interface IAtendimento {
  id?: number;
  situacao?: AtendimentoSituacao;
  dataDeChegada?: Moment;
  dataDeSaida?: Moment;
  observacao?: string;
  atividades?: IAtividade[];
  vendas?: IVenda[];
  anexos?: IAnexoAtendimento[];
  animalId?: number;
}

export class Atendimento implements IAtendimento {
  constructor(
    public id?: number,
    public situacao?: AtendimentoSituacao,
    public dataDeChegada?: Moment,
    public dataDeSaida?: Moment,
    public observacao?: string,
    public atividades?: IAtividade[],
    public vendas?: IVenda[],
    public anexos?: IAnexoAtendimento[],
    public animalId?: number
  ) {}
}
