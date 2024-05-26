import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CatsComponent} from "./cats/cats.component";
import {RouterModule, Routes} from "@angular/router";
import {CatDetailComponent} from "./cat-detail/cat-detail.component";
import {FoodComponent} from "./food/food.component";
import {CustomerComponent} from "./customer/customer.component";
import {CatFoodComponent} from "./cat-food/cat-food.component";
import {PurchaseComponent} from "./purchase/purchase.component";
import {ToyComponent} from "./toy/toy.component";

const routes: Routes = [
  {path: 'cat', component: CatsComponent},
  {path: 'cat/detail/:id', component: CatDetailComponent},
  {path: 'food', component: FoodComponent},
  {path: 'customer', component: CustomerComponent},
  {path: 'catFood', component: CatFoodComponent},
  {path: 'purchase', component: PurchaseComponent},
  {path: 'toy', component: ToyComponent}
]

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
