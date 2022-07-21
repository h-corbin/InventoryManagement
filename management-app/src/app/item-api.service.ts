import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from 'src/models/Item';
import { Warehouse } from 'src/models/Warehouse';

@Injectable({
  providedIn: 'root'
})
export class ItemApiService {

  http :HttpClient;
  url :string = 'http://localhost:8080/inventory-management/item/'
  inventoryUrl :string = 'http://localhost:8080/inventory-management//inventory/warehouse'
  
  constructor(http :HttpClient) { 
    this.http = http;
  }

  findAll() :Observable<any> {
    return this.http.get(this.url);
  }
  
  findByWarehouse(warehouse :Warehouse) :Observable<any> {
    return this.http.put(this.inventoryUrl, warehouse);
  }

  save(item :Item) :Observable<any>{
    return this.http.post(this.url, item);
  }

  delete(item :Item) :Observable<any>{
    return this.http.delete(this.url, {body: item});
  }
}
