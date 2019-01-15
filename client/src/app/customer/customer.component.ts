import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {

  constructor(private http: HttpClient) { }
  customers: any;
  ngOnInit() {
    this.http.get('http://localhost:8080/customer').subscribe(data => {
      this.customers = data;
    });
  }

}
