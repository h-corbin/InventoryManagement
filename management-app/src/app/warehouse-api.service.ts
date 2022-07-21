import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
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

  // saves new warehouse to the database
  save(warehouse :Warehouse) :Observable<any>{
    return this.http.post(this.url, warehouse);
  }

  // deletes warehouse from the database
  // if items are in the database, an error will be thrown
  delete(warehouse :Warehouse) :Observable<any> {
    return this.http.delete(this.url, {body: warehouse}).pipe(
      catchError( throwError )
    )
  }
  
}
