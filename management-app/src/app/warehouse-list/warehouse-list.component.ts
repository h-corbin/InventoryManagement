import { Component, OnInit } from '@angular/core';
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
  
  constructor(warehouseApiService :WarehouseApiService) { 
    this.warehouseApiService = warehouseApiService;
  }

  ngOnInit(): void {
    this.warehouseApiService.findAll().subscribe(resp => {
      // get request should repond with list of warehouses
      this.warehouseList = resp;
    });
  }

}
