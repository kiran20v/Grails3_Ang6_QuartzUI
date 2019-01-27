import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-quartz-jobs-edit',
  templateUrl: './quartz-jobs-edit.component.html',
  styleUrls: ['./quartz-jobs-edit.component.css']
})
export class QuartzJobsEditComponent implements OnInit {

  quartzJob =  {isRunNow:"runNow", cronTrigger:null};

  constructor(private http:HttpClient, private router:Router) { }

  ngOnInit() {
  }

  updateQuartzJob() {
    this.quartzJob.isRunNow =  this.quartzJob.cronTrigger ? "schedule" : "runNow";
    console.log ("Quartz params: ", this.quartzJob);
    this.http.put('http://localhost:8080/quartzJobs', this.quartzJob)
      .subscribe (result => {
        console.log("Result: ",result);
        // let id = result['id'];
        this.router.navigate(['/quartzJobs']);

      }, (errors) => {
          console.log("Errors: ", errors);
      });
  }

}
