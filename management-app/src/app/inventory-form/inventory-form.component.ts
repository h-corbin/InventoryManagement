import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormArray,
  FormControl,
  ValidatorFn
} from '@angular/forms';
import { Inventory } from 'src/models/Inventory';
import { Item } from 'src/models/Item';
import { Warehouse } from 'src/models/Warehouse';
import { ItemApiService } from '../item-api.service';

@Component({
  selector: 'app-inventory-form',
  templateUrl: './inventory-form.component.html',
  styleUrls: ['./inventory-form.component.css']
})
export class InventoryFormComponent implements OnInit {

  form :FormGroup;
  items :any[] = [];
  itemApiService :ItemApiService;
  inventoryFromForm :Inventory = new Inventory()
  itemFromForm :Item = new Item();

  constructor(formBuilder :FormBuilder, itemApiService :ItemApiService) { 
    this.form = formBuilder.group({
      items: ['']
    });
    this.itemApiService = itemApiService;
  }

  ngOnInit(): void {
    this.getItems();
  }

  getItems() {
    this.itemApiService.findAll().subscribe( resp =>
      this.items = resp  
    ) 
  }

  submit(item :Item, inventory :Inventory) :void {
    // this.itemApiService.save(item).subscribe( () => { // send post request to add to the database
    //   //this.inventoryUpdated.emit() // tell warehouse-list component to update
    // }); 
  }

}
