import { ITitulo } from 'app/shared/model/titulo.model';

export interface IFornecedor {
  id?: number;
  nome?: string;
  telefone?: string;
  email?: string;
  pontoReferencia?: string;
  titulos?: ITitulo[];
  enderecoLogradouro?: string;
  enderecoId?: number;
}

export class Fornecedor implements IFornecedor {
  constructor(
    public id?: number,
    public nome?: string,
    public telefone?: string,
    public email?: string,
    public pontoReferencia?: string,
    public titulos?: ITitulo[],
    public enderecoLogradouro?: string,
    public enderecoId?: number
  ) {}
}
