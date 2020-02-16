export interface ICompany {
  id?: number;
  name?: string;
  phone?: string;
  email?: string;
  addressLine?: string;
  city?: string;
  postalCode?: number;
  country?: string;
  url?: string;
  lon?: number;
  lat?: number;
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public name?: string,
    public phone?: string,
    public email?: string,
    public addressLine?: string,
    public city?: string,
    public postalCode?: number,
    public country?: string,
    public url?: string,
    public lon?: number,
    public lat?: number
  ) {}
}
