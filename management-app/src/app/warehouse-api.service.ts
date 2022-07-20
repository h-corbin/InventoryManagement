import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Warehouse } from 'src/models/Warehouse';

@Injectable({
  providedIn: 'root'
})
export class WarehouseApiService {

  http :HttpClient;
  url :string = 'http://localhost:8080/inventory-management/warehouse/'

  constructor(http :HttpClient) { 
    this.http = http;
  }

  // returns all warehouses in Warehouse table of database
  findAll() :Observable<any> {
    return this.http.get(this.url);
  }

  save(warehouse :Warehouse) :Observable<any>{
    return this.http.post(this.url, warehouse);
  }
}
