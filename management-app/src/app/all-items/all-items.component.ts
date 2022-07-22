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

  onDelete(item :Item, i :any){

    if(confirm("Are you sure you want to delete item: " +item.name + "? \n This action can't be reversed.")) {
    
      // attempts to delete warehouse
      this.itemApiService.delete(item).pipe(
        catchError((err) => {
          // can't delete if item is in an inventory
          return throwError(() => new Error("Unable to delete item. \n Make sure it is removed from inventory before deleting."))
        })
      ).subscribe( {
        next: (data) => {alert("Success: warehouse was deleted"); this.itemList.splice(i,1);},
        error: (err) => {alert(err)}
      });

      
    }
  }

}
