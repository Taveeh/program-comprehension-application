import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {CatFood} from "./cat-food.model";
import {catchError, map} from "rxjs/operators";
import {Cat} from "./cat.model";
import {CatFoodId} from "./cat-food-id.model";
import {Purchase} from "./purchase.model";
import {PurchaseWithId} from "./purchase-with-id.model";
import {Customer} from "./customer.model";
import {CustomerSpent} from "./customer-spent.model";

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {
  private purchaseUrl = 'http://localhost:8080/api/purchases';
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private httpClient: HttpClient) {
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    }
  }

  getPurchases(): Observable<any> {
    return this.httpClient.get<Array<Purchase>>(this.purchaseUrl).pipe(
      catchError(this.handleError<Cat[]>('getPurchases', []))
    );
  }

  getCatFoodWithId(id: number): Observable<Purchase> {
    return this.getPurchases().pipe(
      map(purchases => purchases.purchases.find(cf => cf.id === id))
    );
  }

  update(purchase: Purchase): Observable<Purchase> {
    const url = `${this.purchaseUrl}/${purchase.cat.id}&${purchase.customer.id}`;
    return this.httpClient.put<Purchase>(url, purchase).pipe(
      catchError(this.handleError<Purchase>('updatePurchase'))
    );
  }

  add(purchase: Purchase): Observable<any> {
    console.log(purchase);
    return this.httpClient.post<any>(this.purchaseUrl, {
      catId: purchase.cat.id,
      customerId: purchase.customer.id,
      price: purchase.price,
      dateAcquired: purchase.dateAcquired,
      review: purchase.review
    } as PurchaseWithId).pipe(
      catchError(this.handleError<any>('addPurchase'))
    );
  }

  getCustomersThatBoughtBreed(breed: string): Observable<any> {
    return this.httpClient.get<Array<Customer>>(`${this.purchaseUrl}/breed=${breed}`)
      .pipe(catchError(this.handleError('getCustomerThatBoughtBreed')))
  }

  getSortedCustomers(): Observable<any> {
    return this.httpClient.get<Array<CustomerSpent>>(`http://localhost:8080/api/sortedCustomers`)
      .pipe(catchError(this.handleError('getSortedCustomers')))
  }

  getPurchasesWithMinimumReview(review: number): Observable<any> {
    return this.httpClient.get<Array<Purchase>>(`${this.purchaseUrl}/minReview=${review}`)
      .pipe(catchError(this.handleError('getPurchasesWithMinimumReview')))

  }
  delete(catId: number, customerId: number): Observable<any> {
    const url = `${this.purchaseUrl}/${catId}&${customerId}`;
    return this.httpClient.request('DELETE', url, this.httpOptions).pipe(
      catchError(this.handleError('removePurchase'))
    );
  }
}
