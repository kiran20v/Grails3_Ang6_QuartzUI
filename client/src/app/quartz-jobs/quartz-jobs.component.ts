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
    this.http.get('http://localhost:8080/quartzJobs').subscribe(data => {
      this.quartzJobs = data;
    });
  }

  deleteQuartzJob(job) {
    var deleteJob = JSON.stringify({job})
    console.log("Delete job : ",this.quartzJobs[0]);
    this.http.delete('http://localhost:8080/quartzJobs/'+job.jobTriggerName).subscribe(data => {
      console.log("Deleted job: ",data);
      this.quartzJobs.pop(1);
    });
  }

}
