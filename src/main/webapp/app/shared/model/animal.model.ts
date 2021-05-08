import { Moment } from 'moment';
import { IAtendimento } from 'app/shared/model/atendimento.model';
import { IAnimalVacina } from 'app/shared/model/animal-vacina.model';
import { IAnimalAlteracao } from 'app/shared/model/animal-alteracao.model';
import { IAnimalVermifugo } from 'app/shared/model/animal-vermifugo.model';
import { IAnimalCarrapaticida } from 'app/shared/model/animal-carrapaticida.model';
import { IAnimalObservacao } from 'app/shared/model/animal-observacao.model';
import { IAnexo } from 'app/shared/model/anexo.model';
import { IAnimalCio } from 'app/shared/model/animal-cio.model';
import { AnimalSexo } from 'app/shared/model/enumerations/animal-sexo.model';

export interface IAnimal {
  id?: number;
  fotoContentType?: string;
  foto?: any;
  fotoUrl?: string;
  nome?: string;
  sexo?: AnimalSexo;
  pelagem?: string;
  temperamento?: string;
  emAtendimento?: boolean;
  dataNascimento?: Moment;
  atendimentos?: IAtendimento[];
  tipoVacinas?: IAnimalVacina[];
  animalAlteracaos?: IAnimalAlteracao[];
  animalVermifugos?: IAnimalVermifugo[];
  animalCarrapaticidas?: IAnimalCarrapaticida[];
  observacaos?: IAnimalObservacao[];
  anexos?: IAnexo[];
  animalCios?: IAnimalCio[];
  enderecoLogradouro?: string;
  enderecoId?: number;
  veterinarioNome?: string;
  veterinarioId?: number;
  racaNome?: string;
  racaId?: number;
  tutorId?: number;
}

export class Animal implements IAnimal {
  constructor(
    public id?: number,
    public fotoContentType?: string,
    public foto?: any,
    public fotoUrl?: string,
    public nome?: string,
    public sexo?: AnimalSexo,
    public pelagem?: string,
    public temperamento?: string,
    public emAtendimento?: boolean,
    public dataNascimento?: Moment,
    public atendimentos?: IAtendimento[],
    public tipoVacinas?: IAnimalVacina[],
    public animalAlteracaos?: IAnimalAlteracao[],
    public animalVermifugos?: IAnimalVermifugo[],
    public animalCarrapaticidas?: IAnimalCarrapaticida[],
    public observacaos?: IAnimalObservacao[],
    public anexos?: IAnexo[],
    public animalCios?: IAnimalCio[],
    public enderecoLogradouro?: string,
    public enderecoId?: number,
    public veterinarioNome?: string,
    public veterinarioId?: number,
    public racaNome?: string,
    public racaId?: number,
    public tutorId?: number
  ) {
    this.emAtendimento = this.emAtendimento || false;
  }
}
