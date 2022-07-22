import { Component, OnInit } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Item } from 'src/models/Item';
import { ItemApiService } from '../item-api.service';

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
    this.onUpdate();
  }


  onUpdate() {
    this.itemApiService.findAll().subscribe(resp => {
      // get request should repond with list of warehouses
      this.itemList = resp;
    });
  }

  onChangeItem(item :Item) {
    this.itemApiService.update(item).pipe(
      catchError((err) => {
        return throwError(() => new Error("Unable to update item."))
      })
    ).subscribe( {
      next: (data) => {alert("Success: item was updated")},
      error: (err) => {alert(err); this.onUpdate();}
    });
  }

}
