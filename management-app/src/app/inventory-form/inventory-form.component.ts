import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Inventory } from 'src/models/Inventory';
import { Item } from 'src/models/Item';
import { Warehouse } from 'src/models/Warehouse';
import { InventoryApiService } from '../inventory-api.service';
import { ItemApiService } from '../item-api.service';

@Component({
  selector: 'app-inventory-form',
  templateUrl: './inventory-form.component.html',
  styleUrls: ['./inventory-form.component.css']
})
export class InventoryFormComponent implements OnInit {

  items :Item[] = [];
  itemApiService :ItemApiService;
  inventoryAPiService :InventoryApiService
  inventoryFromForm :Inventory = new Inventory()
  inventoryFromFormExisting :Inventory = new Inventory();
  itemFromForm :Item = new Item();
  selectedId = 0;
  @Input() warehouse :Warehouse = new Warehouse();
  @Output() inventoryAdded = new EventEmitter();

  constructor(itemApiService :ItemApiService, inventoryApiService :InventoryApiService) { 
    this.itemApiService = itemApiService;
    this.inventoryAPiService = inventoryApiService;
  }

  ngOnInit(): void {
    this.getItems();
  }

  getItems() {
    this.itemApiService.findAll().subscribe( resp =>
      this.items = resp  
    ) 
  }

  // add to inventory
  add() :void {
    var newInvenetory = new Inventory(this.warehouse.id, this.selectedId, this.inventoryFromFormExisting.quantity);
    this.inventoryAPiService.save(newInvenetory).pipe(
      catchError((err) => {
        return throwError(() => new Error("Unable to add item \n Quantity is invlaid or exceeds warehouse capacity."))
      })
    ).subscribe( {
      next: (resp) => {
        this.inventoryAdded.emit() // refresh inventory list
        this.inventoryFromForm = new Inventory();
      }, 
      error: (err) => {alert(err)}
  });
    
  }

  changeSelectedItem(event :any) {
    this.selectedId = event.target.value;
  }

  // save new item to item table and add it to inventory for this warehouse
  save(item :Item) :void {
    console.log(this.warehouse.id)
    this.itemApiService.save(item).pipe(
      catchError((err) => {
        return throwError(() => new Error("Unable to add item \n Size is invlaid or exceeds warehouse capacity."))
      })
    ).subscribe( {
        next: (resp) => {
          var newItem :Item = resp; 
          var inventory = new Inventory(this.warehouse.id, newItem.id, this.inventoryFromForm.quantity, this.inventoryFromForm.location);
          this.inventoryAPiService.save(inventory).pipe(
            catchError((err) => {
              return throwError(() => new Error("Unable to add item \n Quantity is invlaid or exceeds warehouse capacity."))
            })
          ).subscribe( {
            next: (data) => {
              this.itemFromForm = new Item(); // reset the form
              this.inventoryFromForm = new Inventory();
              this.inventoryAdded.emit() // refresh inventory list
            },
            error: (err) => {alert(err)}
          })
        },
        error: (err) => {alert(err)}
    });
  }

}
