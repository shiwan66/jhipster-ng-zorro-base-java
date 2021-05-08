import { Moment } from 'moment';
import { TipoTitulo } from 'app/shared/model/enumerations/tipo-titulo.model';

export interface ITitulo {
  id?: number;
  isPago?: boolean;
  tipo?: TipoTitulo;
  descricao?: any;
  valor?: number;
  dataEmissao?: Moment;
  dataPagamento?: Moment;
  dataVencimento?: Moment;
  tutorId?: number;
  fornecedorId?: number;
}

export class Titulo implements ITitulo {
  constructor(
    public id?: number,
    public isPago?: boolean,
    public tipo?: TipoTitulo,
    public descricao?: any,
    public valor?: number,
    public dataEmissao?: Moment,
    public dataPagamento?: Moment,
    public dataVencimento?: Moment,
    public tutorId?: number,
    public fornecedorId?: number
  ) {
    this.isPago = this.isPago || false;
  }
}
