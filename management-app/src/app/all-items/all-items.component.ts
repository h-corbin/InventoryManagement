import { Component, OnInit } from '@angular/core';
import { Item } from 'src/models/Item';
import { ItemApiService } from '../item-api.service.service';

@Component({
  selector: 'app-all-items',
  templateUrl: './all-items.component.html',
  styleUrls: ['./all-items.component.css']
})
export class AllItemsComponent implements OnInit {

  itemApiService :ItemApiService;
  itemList :Array<Item> = [];
  
  constructor(itemApiService :ItemApiService) { 
    this.itemApiService = itemApiService;
  }

  ngOnInit(): void {
    this.itemApiService.findAll().subscribe(resp => {
      // get request should repond with list of warehouses
      this.itemList = resp;
    });
  }

}
