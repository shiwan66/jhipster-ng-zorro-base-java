import { Moment } from 'moment';

export interface IAnexo {
  id?: number;
  anexoContentType?: string;
  anexo?: any;
  descricao?: string;
  data?: Moment;
  url?: string;
  urlThumbnail?: string;
  animalId?: number;
}

export class Anexo implements IAnexo {
  constructor(
    public id?: number,
    public anexoContentType?: string,
    public anexo?: any,
    public descricao?: string,
    public data?: Moment,
    public url?: string,
    public urlThumbnail?: string,
    public animalId?: number
  ) {}
}
