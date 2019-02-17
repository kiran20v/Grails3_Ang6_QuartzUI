import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { IndexComponent } from './index/index.component';
import { AppComponent } from './app.component';
import { NavComponent } from './nav/nav.component';
import { NavService } from './nav/nav.service';
import { AppRoutingModule } from "./app-routing.module";
import { HttpClientModule } from "@angular/common/http";
import { CustomerComponent } from './customer/customer.component';
import { QuartzJobsComponent } from './quartz-jobs/quartz-jobs.component';
import { QuartzJobsCreateComponent } from './quartz-jobs-create/quartz-jobs-create.component';
import { QuartzJobsEditComponent } from './quartz-jobs-edit/quartz-jobs-edit.component';
import { QuartzJobsListEditComponent } from './quartz-jobs-list-edit/quartz-jobs-list-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    IndexComponent,
    CustomerComponent,
    QuartzJobsComponent,
    QuartzJobsCreateComponent,
    QuartzJobsEditComponent,
    QuartzJobsListEditComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule.forRoot()
  ],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}, NavService],
  bootstrap: [AppComponent]
})
export class AppModule { }
