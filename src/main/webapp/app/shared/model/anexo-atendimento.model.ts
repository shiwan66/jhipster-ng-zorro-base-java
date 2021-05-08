import { Moment } from 'moment';

export interface IAnexoAtendimento {
  id?: number;
  anexoContentType?: string;
  anexo?: any;
  descricao?: string;
  data?: Moment;
  url?: string;
  urlThumbnail?: string;
  atendimentoId?: number;
}

export class AnexoAtendimento implements IAnexoAtendimento {
  constructor(
    public id?: number,
    public anexoContentType?: string,
    public anexo?: any,
    public descricao?: string,
    public data?: Moment,
    public url?: string,
    public urlThumbnail?: string,
    public atendimentoId?: number
  ) {}
}
