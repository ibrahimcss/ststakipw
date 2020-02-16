export interface IExtraParam {
  id?: number;
  key?: string;
  value?: string;
  companyName?: string;
  companyId?: number;
}

export class ExtraParam implements IExtraParam {
  constructor(public id?: number, public key?: string, public value?: string, public companyName?: string, public companyId?: number) {}
}
