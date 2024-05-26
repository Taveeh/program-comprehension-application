import {Component, OnInit} from '@angular/core';
import {Cat} from "../cat.model";
import {Customer} from "../customer.model";
import {Purchase} from "../purchase.model"
import {PurchaseService} from "../purchase.service";
import {CatsService} from "../cats.service";
import {FoodService} from "../food.service";
import {CustomerService} from "../customer.service";

@Component({
  selector: 'app-purchase',
  templateUrl: './purchase.component.html',
  styleUrls: ['./purchase.component.css']
})
export class PurchaseComponent implements OnInit {

  cats: Cat[];
  customers: Customer[];
  selectedCat?: Cat;
  selectedCustomer?: Customer;


  purchases: Purchase[];
  selectedPurchase?: Purchase;

  constructor(private purchaseService: PurchaseService,
              private catService: CatsService,
              private customerService: CustomerService) {
  }

  makePurchase(price: string, dateAcquired: string, review: string) {
    console.log(price)
    console.log(dateAcquired)
    console.log(review)
    this.purchaseService.add({
      cat: this.selectedCat,
      customer: this.selectedCustomer,
      price: Number(price),
      dateAcquired: new Date(dateAcquired),
      review: Number(review)
    } as Purchase).subscribe(
      _ => this.getPurchases()
    )
  }

  filterWithReview(review: string) {
    this.purchaseService.getPurchasesWithMinimumReview(Number(review)).subscribe(purchases => this.purchases = purchases.purchases);
  }
  onSelect(purchase: Purchase) {
    this.selectedPurchase = purchase;
  }

  onSelectCat(cat: Cat) {
    this.selectedCat = cat;
  }

  onSelectCustomer(customer: Customer) {
    this.selectedCustomer = customer;
  }

  delete(purchase: Purchase) {
    this.purchaseService.delete(purchase.cat.id, purchase.customer.id).subscribe(
      _ => this.getPurchases()
    )
  }

  getPurchases() {
    this.purchaseService.getPurchases().subscribe(
      purchases => this.purchases = purchases.purchases
    )
  }

  getCats() {
    this.catService.getCats().subscribe(
      cats => this.cats = cats.cats
    )
  }

  getCustomers() {
    this.customerService.getCustomers().subscribe(
      customers => this.customers = customers.customers
    )
  }

  ngOnInit(): void {
    this.getPurchases();
    this.getCats();
    this.getCustomers();
  }

}
