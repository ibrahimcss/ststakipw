import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.StstakipCompanyModule)
      },
      {
        path: 'student',
        loadChildren: () => import('./student/student.module').then(m => m.StstakipStudentModule)
      },
      {
        path: 'paket-detay',
        loadChildren: () => import('./paket-detay/paket-detay.module').then(m => m.StstakipPaketDetayModule)
      },
      {
        path: 'order-paket',
        loadChildren: () => import('./order-paket/order-paket.module').then(m => m.StstakipOrderPaketModule)
      },
      {
        path: 'vehicle',
        loadChildren: () => import('./vehicle/vehicle.module').then(m => m.StstakipVehicleModule)
      },
      {
        path: 'vehicle-tracking',
        loadChildren: () => import('./vehicle-tracking/vehicle-tracking.module').then(m => m.StstakipVehicleTrackingModule)
      },
      {
        path: 'travel-path',
        loadChildren: () => import('./travel-path/travel-path.module').then(m => m.StstakipTravelPathModule)
      },
      {
        path: 'student-to-travel-path',
        loadChildren: () => import('./student-to-travel-path/student-to-travel-path.module').then(m => m.StstakipStudentToTravelPathModule)
      },
      {
        path: 'student-tracking',
        loadChildren: () => import('./student-tracking/student-tracking.module').then(m => m.StstakipStudentTrackingModule)
      },
      {
        path: 'notification',
        loadChildren: () => import('./notification/notification.module').then(m => m.StstakipNotificationModule)
      },
      {
        path: 'works-in',
        loadChildren: () => import('./works-in/works-in.module').then(m => m.StstakipWorksInModule)
      },
      {
        path: 'customer-of',
        loadChildren: () => import('./customer-of/customer-of.module').then(m => m.StstakipCustomerOfModule)
      },
      {
        path: 'extra-param',
        loadChildren: () => import('./extra-param/extra-param.module').then(m => m.StstakipExtraParamModule)
      },
      {
        path: 'family-member',
        loadChildren: () => import('./family-member/family-member.module').then(m => m.StstakipFamilyMemberModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class StstakipEntityModule {}
