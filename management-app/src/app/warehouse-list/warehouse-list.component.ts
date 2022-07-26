import { Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { catchError, of, throwError } from 'rxjs';
import { Warehouse } from 'src/models/Warehouse';
import { WarehouseApiService } from '../warehouse-api.service';

@Component({
  selector: 'app-warehouse-list',
  templateUrl: './warehouse-list.component.html',
  styleUrls: ['./warehouse-list.component.css']
})
export class WarehouseListComponent implements OnInit {

  warehouseApiService :WarehouseApiService;
  warehouseList :Array<Warehouse> = [];
  warehouseClicked :Warehouse = new Warehouse();
  hideItems :boolean = true;
  
  constructor(warehouseApiService :WarehouseApiService) { 
    this.warehouseApiService = warehouseApiService;
  }

  ngOnInit(): void {
    this.onUpdate();
  }


  onUpdate() {
    this.warehouseApiService.findAll().subscribe(resp => {
      // get request should respond with list of warehouses
      this.warehouseList = resp;
    });
  }

  onUpdateSinlgeWarehouse(warehouse :Warehouse) {
    this.warehouseApiService.update(warehouse).pipe(
      catchError((err) => {
        // can't delete if there are any items in the warehouse inventory
        return throwError(() => new Error("Unable to update Warehouse"))
      })
    ).subscribe( {
      next: (data) => {alert("Success: warehouse was updated")},
      error: (err) => {alert(err); this.onUpdate();}
    });
  }

  onDelete(warehouse :Warehouse, i :any){

    if(confirm("Are you sure you want to delete warehouse: " +warehouse.name + "? \n This action can't be reversed.")) {
    
      // attempts to delete warehouse
      this.warehouseApiService.delete(warehouse).pipe(
        catchError((err) => {
          // can't delete if there are any items in the warehouse inventory
          return throwError(() => new Error("Unable to delete Warehouse \n Make sure warehouse is empty before deleting."))
        })
      ).subscribe( {
        next: (data) => {alert("Success: warehouse was deleted"); this.warehouseList.splice(i,1);},
        error: (err) => {alert(err)}
      });

      
    }
  }

  showInventory(warehouse :Warehouse) {
    this.warehouseClicked = Object.assign({},warehouse)
    this.hideItems = false;
  }

}
