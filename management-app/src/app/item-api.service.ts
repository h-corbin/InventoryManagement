import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from 'src/models/Item';

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

  save(item :Item) :Observable<any>{
    return this.http.post(this.url, item);
  }
}
