import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-quartz-jobs',
  templateUrl: './quartz-jobs.component.html',
  styleUrls: ['./quartz-jobs.component.css']
})
export class QuartzJobsComponent implements OnInit {

  constructor(private http: HttpClient) { }
  quartzJobs : any;
  ngOnInit() {
    this.http.get('http://localhost:8080/customer').subscribe(data => {
      this.quartzJobs = data;
    });
  }

}
