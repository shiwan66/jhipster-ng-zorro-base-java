export interface IVendaConsumo {
  id?: number;
  quantidade?: number;
  valorUnitario?: number;
  valorTotal?: number;
  vendaId?: number;
  consumoId?: number;
}

export class VendaConsumo implements IVendaConsumo {
  constructor(
    public id?: number,
    public quantidade?: number,
    public valorUnitario?: number,
    public valorTotal?: number,
    public vendaId?: number,
    public consumoId?: number
  ) {}
}
