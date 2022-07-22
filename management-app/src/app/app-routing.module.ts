import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllInventoryComponent } from './all-inventory/all-inventory.component';
import { AllItemsComponent } from './all-items/all-items.component';
import { InventoryFormComponent } from './inventory-form/inventory-form.component';
import { WarehouseInventoryComponent } from './warehouse-inventory/warehouse-inventory.component';
import { WarehouseListComponent } from './warehouse-list/warehouse-list.component';

const routes: Routes = [
  {
    path : 'warehouse-list',
    component : WarehouseListComponent
  }, {
    path : 'item-list',
    component : AllItemsComponent
  }, {
    path : 'inventory-list',
    component : AllInventoryComponent
  }, {
    path : 'warehouse-inventory',
    component : WarehouseInventoryComponent
  }
  , {
    path : 'inventory-form',
    component : InventoryFormComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
