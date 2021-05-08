export interface IEndereco {
  id?: number;
  cep?: string;
  logradouro?: string;
  numero?: string;
  complemento?: string;
  bairro?: string;
  localidade?: string;
  uf?: string;
  ibge?: string;
}

export class Endereco implements IEndereco {
  constructor(
    public id?: number,
    public cep?: string,
    public logradouro?: string,
    public numero?: string,
    public complemento?: string,
    public bairro?: string,
    public localidade?: string,
    public uf?: string,
    public ibge?: string
  ) {}
}
