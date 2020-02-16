export interface IWorksIn {
  id?: number;
  tcId?: number;
  phone?: string;
  enabled?: boolean;
  employeeLogin?: string;
  employeeId?: number;
  companyName?: string;
  companyId?: number;
}

export class WorksIn implements IWorksIn {
  constructor(
    public id?: number,
    public tcId?: number,
    public phone?: string,
    public enabled?: boolean,
    public employeeLogin?: string,
    public employeeId?: number,
    public companyName?: string,
    public companyId?: number
  ) {
    this.enabled = this.enabled || false;
  }
}
