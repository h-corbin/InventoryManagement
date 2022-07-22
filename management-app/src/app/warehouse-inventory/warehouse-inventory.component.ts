import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { ExtendedInventory } from 'src/models/ExtendedInventory';
import { Inventory } from 'src/models/Inventory';
import { Warehouse } from 'src/models/Warehouse';
import { InventoryApiService } from '../inventory-api.service';
import { ItemApiService } from '../item-api.service';
import { WarehouseApiService } from '../warehouse-api.service';

@Component({
  selector: 'app-warehouse-inventory',
  templateUrl: './warehouse-inventory.component.html',
  styleUrls: ['./warehouse-inventory.component.css']
})
export class WarehouseInventoryComponent implements OnInit {

  itemApiService :ItemApiService;
  warehouseApiService :WarehouseApiService;
  inventoryApiService :InventoryApiService;
  itemList :Array<ExtendedInventory> = [];
  @Input() warehouse :Warehouse = new Warehouse();
  @Output() inventoryDeleted = new EventEmitter();
  @Output() inventoryAdded = new EventEmitter();

  
  constructor(itemApiService :ItemApiService, warehouseApiService :WarehouseApiService, inventoryApiService :InventoryApiService) { 
    this.itemApiService = itemApiService;
    this.warehouseApiService = warehouseApiService;
    this.inventoryApiService = inventoryApiService;
  }

  ngOnInit() :void {
  }

  ngOnChanges() {
    this.inventoryApiService.findByWarehouse(this.warehouse).subscribe( resp =>
      this.itemList = resp  
    )
  }


  onDelete(extendedInventory :ExtendedInventory, i :any){
    var inventory = new Inventory(extendedInventory.warehouseId, extendedInventory.itemId);
    if(confirm("Are you sure you want to delete item: " +extendedInventory.itemName + "from the warehouse? \n This action can't be reversed.")) {
      this.inventoryApiService.delete(inventory).subscribe(); // delete item
      this.itemList.splice(i,1); // remove from table
    }
  }

  deleteAll(){
    if(confirm("Are you sure you want to delete ALL items from the warehouse? \n This action can't be reversed.")) {
      var inventoryList = []
      for (var item of this.itemList) {
        inventoryList.push(new Inventory(item.warehouseId, item.itemId))
      }

      this.inventoryApiService.deleteAll(inventoryList).subscribe(); 
      this.inventoryDeleted.emit()
    }
  }

  onUpdateInventory(extendedInventory :ExtendedInventory) {
    var inventory = new Inventory(extendedInventory.warehouseId, extendedInventory.itemId);
    this.inventoryApiService.update(inventory).pipe(
      catchError((err) => {
        // can't delete if there are any items in the warehouse inventory
        return throwError(() => new Error("Unable to update Warehouse"))
      })
    ).subscribe( {
      next: (data) => {alert("Success: warehouse was updated")},
      error: (err) => {alert(err); this.ngOnChanges();}
    });
  }

}
