import { NgModule } from '@angular/core';
import {RouterModule,Routes} from '@angular/router';
import {IndexComponent} from "./index/index.component";
import { CustomerComponent } from './customer/customer.component';
import { QuartzJobsComponent } from './quartz-jobs/quartz-jobs.component';

const routes: Routes = [
  {path: '', redirectTo: 'index', pathMatch: 'full'},
  {path: 'index', component: IndexComponent},

  {
    path: 'customers',
    component: CustomerComponent,
    data: { title: 'Customer List' }
  },
  { path: '',
    redirectTo: '/customers',
    pathMatch: 'full'
  },

  {
    path: 'quartzJobs',
    component: QuartzJobsComponent,
    data: { title: 'Quartz Jobs' }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}