import { formatCurrency } from '@angular/common';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Warehouse } from 'src/models/Warehouse';
import { WarehouseApiService } from '../warehouse-api.service';

@Component({
  selector: 'app-warehouse-form',
  templateUrl: './warehouse-form.component.html',
  styleUrls: ['./warehouse-form.component.css']
})
export class WarehouseFormComponent implements OnInit {

  warehouseFromForm :Warehouse;
  warehouseApiService :WarehouseApiService;

  @Output() warehousesUpdated = new EventEmitter();


  constructor(warehouseApiService :WarehouseApiService) { 
    this.warehouseFromForm = new Warehouse();
    this.warehouseApiService = warehouseApiService;
  }

  ngOnInit(): void {
  }

  
  submit(warehouse :Warehouse) :void {
    this.warehouseApiService.save(warehouse).subscribe(); // send post request to add to the database
    this.warehouseFromForm = new Warehouse; // reset the form
    this.warehousesUpdated.emit() // tell warehouse-list component to update
  }

}
