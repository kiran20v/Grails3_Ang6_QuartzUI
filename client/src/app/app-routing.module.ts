import { NgModule } from '@angular/core';
import {RouterModule,Routes} from '@angular/router';
import {IndexComponent} from "./index/index.component";
import { CustomerComponent } from './customer/customer.component';
import { QuartzJobsComponent } from './quartz-jobs/quartz-jobs.component';
import { QuartzJobsCreateComponent } from './quartz-jobs-create/quartz-jobs-create.component';
import { QuartzJobsListEditComponent } from './quartz-jobs-list-edit/quartz-jobs-list-edit.component';

const routes: Routes = [
  {path: '', redirectTo: 'index', pathMatch: 'full'},
  //{path: 'index', component: IndexComponent},

  {path: 'index', component: QuartzJobsListEditComponent},

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
  },

  {
    path: 'quartzJobsList',
    component: QuartzJobsListEditComponent,
    data: { title: 'Quartz Jobs' }
  },
  {
    path: 'quartzJobs-create',
    component: QuartzJobsCreateComponent,
    data: { title: 'Quartz Jobs create' }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
