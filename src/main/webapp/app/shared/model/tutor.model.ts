import { Moment } from 'moment';
import { ITitulo } from 'app/shared/model/titulo.model';
import { IAnimal } from 'app/shared/model/animal.model';
import { Sexo } from 'app/shared/model/enumerations/sexo.model';

export interface ITutor {
  id?: number;
  nome?: string;
  sobrenome?: string;
  telefone1?: string;
  telefone2?: string;
  email?: string;
  fotoContentType?: string;
  foto?: any;
  fotoUrl?: string;
  cpf?: string;
  sexo?: Sexo;
  dataCadastro?: Moment;
  userFirstName?: string;
  userId?: number;
  titulos?: ITitulo[];
  animals?: IAnimal[];
  enderecoLogradouro?: string;
  enderecoId?: number;
}

export class Tutor implements ITutor {
  constructor(
    public id?: number,
    public nome?: string,
    public sobrenome?: string,
    public telefone1?: string,
    public telefone2?: string,
    public email?: string,
    public fotoContentType?: string,
    public foto?: any,
    public fotoUrl?: string,
    public cpf?: string,
    public sexo?: Sexo,
    public dataCadastro?: Moment,
    public userFirstName?: string,
    public userId?: number,
    public titulos?: ITitulo[],
    public animals?: IAnimal[],
    public enderecoLogradouro?: string,
    public enderecoId?: number
  ) {}
}
