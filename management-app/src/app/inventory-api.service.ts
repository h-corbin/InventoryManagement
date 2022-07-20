import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Warehouse } from 'src/models/Warehouse';

@Injectable({
  providedIn: 'root'
})
export class InventoryApiService {

  http :HttpClient;
  url :string = 'http://localhost:8080/inventory-management/item/'
  
  constructor(http :HttpClient) { 
    this.http = http;
  }


  // returns all warehouses in Warehouse table of database
  InventoryByWarehouse(warehouse :Warehouse) :Observable<any> {
    return this.http.get(this.url + "warehouse")
  }
}
