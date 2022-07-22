import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Inventory } from 'src/models/Inventory';
import { Warehouse } from 'src/models/Warehouse';

@Injectable({
  providedIn: 'root'
})
export class InventoryApiService {

  http :HttpClient;
  url :string = 'http://localhost:8080/inventory-management/inventory/'
  
  constructor(http :HttpClient) { 
    this.http = http;
  }


  save(inventory :Inventory) :Observable<any> {
    return this.http.post(this.url, inventory)
  }

  update(inventory :Inventory) :Observable<any> {
    return this.http.put(this.url, inventory)
  }

  // returns all rows in the extended inventory (inventory join warehouse join item)
  findall() :Observable<any> {
    return this.http.get(this.url)
  }

  // returns all rows with itemId matching the Inventory object
  InventoryByItem(inventory :Inventory) :Observable<any> {
    return this.http.put(this.url + "item", inventory)
  }

  // returns all rows with warehouseId matching the Inventory object
  InventoryByWarehouse(inventory :Inventory) :Observable<any> {
    return this.http.put(this.url + "warehouse", inventory)
  }


  findByWarehouse(warehouse :Warehouse) :Observable<any> {
    return this.http.put(this.url+ "warehouse", warehouse);
  }

  delete(inventory :Inventory) :Observable<any> {
    return this.http.delete(this.url, {body: inventory})
  }

  deleteAll(inventoryList :Array<Inventory>) :Observable<any> {
    return this.http.delete(this.url + "deleteAll", {body: inventoryList});
  }
}
