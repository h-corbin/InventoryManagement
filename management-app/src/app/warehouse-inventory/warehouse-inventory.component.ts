import { Component, OnInit } from '@angular/core';
import { ExtendedInventory } from 'src/models/ExtendedInventory';
import { InventoryApiService } from '../inventory-api.service';

@Component({
  selector: 'app-warehouse-inventory',
  templateUrl: './warehouse-inventory.component.html',
  styleUrls: ['./warehouse-inventory.component.css']
})
export class WarehouseInventoryComponent implements OnInit {

  constructor() { 
  }

  ngOnInit(): void {
  }

}
