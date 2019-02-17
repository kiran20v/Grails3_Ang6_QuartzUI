import { Component, OnInit } from '@angular/core';
import {QuartzJobsComponent} from "../quartz-jobs/quartz-jobs.component";

@Component({
  selector: 'app-quartz-jobs-list-edit',
  templateUrl: './quartz-jobs-list-edit.component.html',
  styleUrls: ['./quartz-jobs-list-edit.component.css']
})
export class QuartzJobsListEditComponent implements OnInit {

  showView : any;

  constructor() { }

  ngOnInit() {
  }



}
