import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-quartz-jobs',
  templateUrl: './quartz-jobs.component.html',
  styleUrls: ['./quartz-jobs.component.css']
})
export class QuartzJobsComponent implements OnInit {

  // showView : any;
  quartzJobs : any;
  quartzEditJobs : any;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.http.get('http://localhost:8080/quartzJobs').subscribe(data => {
      this.quartzJobs = data;
    });
  }

  show(view) {

    console.log("Show view : "+view);

    // if(view) {
    //   showView = true;
    // }

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
