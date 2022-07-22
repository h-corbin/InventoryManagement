import { Component, OnInit } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { ExtendedInventory } from 'src/models/ExtendedInventory';
import { Inventory } from 'src/models/Inventory';
import { InventoryApiService } from '../inventory-api.service';

@Component({
  selector: 'app-all-inventory',
  templateUrl: './all-inventory.component.html',
  styleUrls: ['./all-inventory.component.css']
})
export class AllInventoryComponent implements OnInit {

  inventoryApiService :InventoryApiService;
  extendedInventoryList :Array<ExtendedInventory> = [];
  
  constructor(inventoryApiService :InventoryApiService) { 
    this.inventoryApiService = inventoryApiService;
  }

  ngOnInit(): void {
    this.onUpdate();
  }

  onUpdate() :void {
    this.inventoryApiService.findall().subscribe(resp => {
      // get request should repond with extended inventory list
      this.extendedInventoryList= resp;
    });
  }

  onChangeInventory(extendedInventory :ExtendedInventory) {
    var inventory = new Inventory(extendedInventory.warehouseId, extendedInventory.itemId, extendedInventory.quantity, extendedInventory.location)
    this.inventoryApiService.update(inventory).pipe(
      catchError((err) => {
        return throwError(() => new Error("Unable to update inventory listing."))
      })
    ).subscribe( {
      next: (data) => {alert("Success: inventory was updated")},
      error: (err) => {alert(err); this.onUpdate();}
    });
  }

  onDeleteInventory(extendedInventory: ExtendedInventory, i :any) {
    var inventory = new Inventory(extendedInventory.warehouseId, extendedInventory.itemId)
    if(confirm("Are you sure you want to delete item: " +extendedInventory.itemName + "from the warehouse? \n This action can't be reversed.")) {
      this.inventoryApiService.delete(inventory).subscribe(); // delete item
      this.extendedInventoryList.splice(i,1); // remove from table
    }
  }

}
