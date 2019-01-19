import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-quartz-jobs-create',
  templateUrl: './quartz-jobs-create.component.html',
  styleUrls: ['./quartz-jobs-create.component.css']
})
export class QuartzJobsCreateComponent implements OnInit {

  quartzJob = {isRunNow:"runNow", cronTrigger:null};
  constructor(private http:HttpClient, private router: Router) { }

  ngOnInit() {
    
  }

  saveQuartzJob() {
    this.quartzJob.isRunNow =  this.quartzJob.cronTrigger ? "schedule" : "runNow";
    
    this.http.post('http://localhost:8080/quartzJobs', this.quartzJob)
      .subscribe (result => {
        let id = result['id'];
        this.router.navigate(['/quartz-jobs/']);
      }, (err) => {
          console.log(err);
      });
  }
}
