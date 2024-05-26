import {Cat} from "./cat.model";
import {Customer} from "./customer.model";

export interface Purchase {
  cat: Cat;
  customer: Customer;
  price: number;
  dateAcquired: Date;
  review: number;
}
