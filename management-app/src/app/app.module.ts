import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { WarehouseListComponent } from './warehouse-list/warehouse-list.component';
import { HttpClientModule } from '@angular/common/http';
import { AllItemsComponent } from './all-items/all-items.component';
import { WarehouseInventoryComponent } from './warehouse-inventory/warehouse-inventory.component';
import { WarehouseFormComponent } from './warehouse-form/warehouse-form.component';
import { FormsModule } from '@angular/forms';
import { ItemFormComponent } from './item-form/item-form.component';
import { ButtonModule } from 'primeng/button';
import { AllInventoryComponent } from './all-inventory/all-inventory.component';

@NgModule({
  declarations: [
    AppComponent,
    WarehouseListComponent,
    AllItemsComponent,
    WarehouseInventoryComponent,
    WarehouseFormComponent,
    ItemFormComponent,
    AllInventoryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
