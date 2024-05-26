import {Component, OnInit} from '@angular/core';
import {Customer} from "../customer.model";
import {CustomerService} from "../customer.service";
import {PurchaseService} from "../purchase.service";

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {

  selectedCustomer?: Customer;
  customers: Customer[];

  getCustomers() {
    this.customerService.getCustomers().subscribe(
      customers => {
        console.log(customers)
        this.customers = customers.customers
      }
    )
  }

  filterByBreed(breed: string) {
    this.purchaseService.getCustomersThatBoughtBreed(breed).subscribe(customers => this.customers = customers.customers)
  }

  sortBySpent() {
    this.purchaseService.getSortedCustomers().subscribe(customers => {
      console.log(customers);
      let cust = customers.customersSpentCash;
      for (let i in cust) {
        cust[i] = cust[i].customer;
      }
      this.customers = cust;
    })
  }

  delete(customer: Customer) {
    this.customerService.delete(customer.id).subscribe(_ => this.getCustomers())
  }

  onSelect(customer: Customer) {
    this.selectedCustomer = customer;
  }

  save() {
    this.customerService.update(this.selectedCustomer).subscribe();
  }

  add(name: string, phone: string) {
    name = name.trim();
    phone = phone.trim();
    if (!name || !phone) {
      alert("Please enter valid infomration");
      return;
    }
    this.customerService.add({
        name: name,
        phoneNumber: phone
      } as Customer
    ).subscribe(
      _ => this.getCustomers()
    )
  }

  addCustomerFormMethod(data) {
    console.log(data);
    let name = data.customerName.trim();
    let phoneNumber = data.customerPhoneNumber.trim();

    if (!name || !phoneNumber) {
      alert("Please fill in all fields")
      return;
    }
    if (phoneNumber.length != 10) {
      alert("Phone number must have 10 digits");
      return;
    }
    this.customerService.add({name, phoneNumber} as Customer).subscribe(_ => this.getCustomers())
  }

  constructor(private customerService: CustomerService, private purchaseService: PurchaseService) {
  }

  ngOnInit(): void {
    this.getCustomers();
  }

}
