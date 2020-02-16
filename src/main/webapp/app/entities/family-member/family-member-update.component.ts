import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFamilyMember, FamilyMember } from 'app/shared/model/family-member.model';
import { FamilyMemberService } from './family-member.service';
import { ICustomerOf } from 'app/shared/model/customer-of.model';
import { CustomerOfService } from 'app/entities/customer-of/customer-of.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student/student.service';

type SelectableEntity = ICustomerOf | IStudent;

@Component({
  selector: 'jhi-family-member-update',
  templateUrl: './family-member-update.component.html'
})
export class FamilyMemberUpdateComponent implements OnInit {
  isSaving = false;
  customerofs: ICustomerOf[] = [];
  students: IStudent[] = [];

  editForm = this.fb.group({
    id: [],
    parentId: [null, Validators.required],
    childId: [null, Validators.required]
  });

  constructor(
    protected familyMemberService: FamilyMemberService,
    protected customerOfService: CustomerOfService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ familyMember }) => {
      this.updateForm(familyMember);

      this.customerOfService.query().subscribe((res: HttpResponse<ICustomerOf[]>) => (this.customerofs = res.body || []));

      this.studentService.query().subscribe((res: HttpResponse<IStudent[]>) => (this.students = res.body || []));
    });
  }

  updateForm(familyMember: IFamilyMember): void {
    this.editForm.patchValue({
      id: familyMember.id,
      parentId: familyMember.parentId,
      childId: familyMember.childId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const familyMember = this.createFromForm();
    if (familyMember.id !== undefined) {
      this.subscribeToSaveResponse(this.familyMemberService.update(familyMember));
    } else {
      this.subscribeToSaveResponse(this.familyMemberService.create(familyMember));
    }
  }

  private createFromForm(): IFamilyMember {
    return {
      ...new FamilyMember(),
      id: this.editForm.get(['id'])!.value,
      parentId: this.editForm.get(['parentId'])!.value,
      childId: this.editForm.get(['childId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamilyMember>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
