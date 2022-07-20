import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Item } from 'src/models/Item';
import { ItemApiService } from '../item-api.service';

@Component({
  selector: 'app-item-form',
  templateUrl: './item-form.component.html',
  styleUrls: ['./item-form.component.css']
})
export class ItemFormComponent implements OnInit {

  itemFromForm :Item;
  itemApiService :ItemApiService;

  @Output() warehousesUpdated = new EventEmitter();


  constructor(itemApiService :ItemApiService) { 
    this.itemFromForm = new Item();
    this.itemApiService = itemApiService;
  }

  ngOnInit(): void {
  }

  submit(item :Item) :void {
    this.itemApiService.save(item).subscribe( () => { // send post request to add to the database
      this.warehousesUpdated.emit() // tell warehouse-list component to update
    }); 

    this.itemFromForm = new Item(); // reset the form
  }

}
