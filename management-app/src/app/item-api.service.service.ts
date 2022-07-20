import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ItemApiService {

  http :HttpClient;
  url :string = 'http://localhost:8080/inventory-management/item/'
  
  constructor(http :HttpClient) { 
    this.http = http;
  }


  // returns all warehouses in Warehouse table of database
  findAll() :Observable<any> {
    return this.http.get(this.url);
  }
}
