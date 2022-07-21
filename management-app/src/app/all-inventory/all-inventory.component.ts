import { Component, OnInit } from '@angular/core';
import { ExtendedInventory } from 'src/models/ExtendedInventory';
import { InventoryApiService } from '../inventory-api.service';

@Component({
  selector: 'app-all-inventory',
  templateUrl: './all-inventory.component.html',
  styleUrls: ['./all-inventory.component.css']
})
export class AllInventoryComponent implements OnInit {

  inventoryApiService :InventoryApiService;
  expandedInventoryList :Array<ExtendedInventory> = [];
  
  constructor(inventoryApiService :InventoryApiService) { 
    this.inventoryApiService = inventoryApiService;
  }

  ngOnInit(): void {
    this.inventoryApiService.findall().subscribe(resp => {
      // get request should repond with extended inventory list
      this.expandedInventoryList= resp;
    });
  }

}
