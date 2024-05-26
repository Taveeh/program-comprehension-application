import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Cat} from "./cat.model";
import {catchError, map} from "rxjs/operators";
import {CatFood} from "./cat-food.model";
import {CatFoodId} from "./cat-food-id.model";

@Injectable({
  providedIn: 'root'
})
export class CatFoodService {
  private catFoodUrl = 'http://localhost:8080/api/catFoods';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor(private httpClient: HttpClient) {
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    }
  }

  getCatFoods(): Observable<any> {
    return this.httpClient.get<Array<CatFood>>(this.catFoodUrl).pipe(
      catchError(this.handleError<Cat[]>('getCatFoods', []))
    );
  }

  getCatFoodWithId(id: number): Observable<CatFood> {
    return this.getCatFoods().pipe(
      map(catFoods => catFoods.catFoods.find(cf => cf.id === id))
    );
  }

  update(catFood: CatFood): Observable<CatFood> {
    const url = `${this.catFoodUrl}/${catFood.cat.id}&${catFood.food.id}`;
    return this.httpClient.put<CatFood>(url, catFood).pipe(
      catchError(this.handleError<CatFood>('updateCatFood'))
    );
  }

  add(catFood: CatFood): Observable<any> {
    console.log(catFood);
    return this.httpClient.post<any>(this.catFoodUrl, {catId: catFood.cat.id, foodId: catFood.food.id} as CatFoodId).pipe(
      catchError(this.handleError<any>('addCatFood'))
    );
  }

  delete(catId: number, foodId: number): Observable<any> {
    const url = `${this.catFoodUrl}/${catId}&${foodId}`;
    return this.httpClient.request('DELETE', url, this.httpOptions).pipe(
      catchError(this.handleError('removeCatFood'))
    );
  }

  filterCatsThatEatCertainFood(foodId: number): Observable<any> {
    return this.httpClient.get<any>(`${this.catFoodUrl}/${foodId}`).pipe(
      catchError(this.handleError<any>('filterCatsThatEatCertainFood'))
    );
  }
}
