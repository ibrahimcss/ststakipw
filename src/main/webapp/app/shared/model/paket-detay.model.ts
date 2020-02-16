import { PaketEnum } from 'app/shared/model/enumerations/paket-enum.model';

export interface IPaketDetay {
  id?: number;
  name?: PaketEnum;
  description?: string;
  price?: number;
  vehicleQuota?: number;
  photoContentType?: string;
  photo?: any;
  enabled?: boolean;
  year?: number;
}

export class PaketDetay implements IPaketDetay {
  constructor(
    public id?: number,
    public name?: PaketEnum,
    public description?: string,
    public price?: number,
    public vehicleQuota?: number,
    public photoContentType?: string,
    public photo?: any,
    public enabled?: boolean,
    public year?: number
  ) {
    this.enabled = this.enabled || false;
  }
}
