import { Moment } from 'moment';
import { ICategoria } from 'app/shared/model/categoria.model';

export interface IProduto {
  id?: number;
  imagemContentType?: string;
  imagem?: any;
  nome?: string;
  descricao?: any;
  preco?: number;
  data?: Moment;
  hora?: Moment;
  categorias?: ICategoria[];
}

export class Produto implements IProduto {
  constructor(
    public id?: number,
    public imagemContentType?: string,
    public imagem?: any,
    public nome?: string,
    public descricao?: any,
    public preco?: number,
    public data?: Moment,
    public hora?: Moment,
    public categorias?: ICategoria[]
  ) {}
}
