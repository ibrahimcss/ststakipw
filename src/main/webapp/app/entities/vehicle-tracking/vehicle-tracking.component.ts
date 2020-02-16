import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehicleTracking } from 'app/shared/model/vehicle-tracking.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { VehicleTrackingService } from './vehicle-tracking.service';
import { VehicleTrackingDeleteDialogComponent } from './vehicle-tracking-delete-dialog.component';

@Component({
  selector: 'jhi-vehicle-tracking',
  templateUrl: './vehicle-tracking.component.html'
})
export class VehicleTrackingComponent implements OnInit, OnDestroy {
  vehicleTrackings?: IVehicleTracking[];
  eventSubscriber?: Subscription;
  currentSearch: string;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected vehicleTrackingService: VehicleTrackingService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    if (this.currentSearch) {
      this.vehicleTrackingService
        .search({
          page: pageToLoad - 1,
          query: this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IVehicleTracking[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
          () => this.onError()
        );
      return;
    }

    this.vehicleTrackingService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IVehicleTracking[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadPage(1);
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInVehicleTrackings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVehicleTracking): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVehicleTrackings(): void {
    this.eventSubscriber = this.eventManager.subscribe('vehicleTrackingListModification', () => this.loadPage());
  }

  delete(vehicleTracking: IVehicleTracking): void {
    const modalRef = this.modalService.open(VehicleTrackingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vehicleTracking = vehicleTracking;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IVehicleTracking[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.ngbPaginationPage = this.page;
    this.router.navigate(['/vehicle-tracking'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        search: this.currentSearch,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.vehicleTrackings = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
