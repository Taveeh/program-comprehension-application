import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { CatsComponent } from './cats/cats.component';
import { CatsListComponent } from './cats-list/cats-list.component';
import {RouterModule} from "@angular/router";
import { AppRoutingModule } from './app-routing.module';
import {HttpClientModule} from "@angular/common/http";
import { CatDetailComponent } from './cat-detail/cat-detail.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { FoodComponent } from './food/food.component';
import { CustomerComponent } from './customer/customer.component';
import { CatFoodComponent } from './cat-food/cat-food.component';
import { PurchaseComponent } from './purchase/purchase.component';
import { ToyComponent } from './toy/toy.component';

@NgModule({
  declarations: [
    AppComponent,
    CatsComponent,
    CatsListComponent,
    CatDetailComponent,
    FoodComponent,
    CustomerComponent,
    CatFoodComponent,
    PurchaseComponent,
    ToyComponent
  ],
    imports: [
        BrowserModule,
        RouterModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
