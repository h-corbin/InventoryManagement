import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllItemsComponent } from './all-items/all-items.component';
import { WarehouseListComponent } from './warehouse-list/warehouse-list.component';

const routes: Routes = [
  {
    path : 'warehouse-list',
    component : WarehouseListComponent
  }, {
    path : 'item-list',
    component : AllItemsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
