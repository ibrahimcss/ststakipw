export interface IFamilyMember {
  id?: number;
  parentTcId?: string;
  parentId?: number;
  childTcId?: string;
  childId?: number;
}

export class FamilyMember implements IFamilyMember {
  constructor(
    public id?: number,
    public parentTcId?: string,
    public parentId?: number,
    public childTcId?: string,
    public childId?: number
  ) {}
}
