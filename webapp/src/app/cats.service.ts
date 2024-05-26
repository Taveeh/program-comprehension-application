import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpRequest} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Cat} from "./cat.model";
import {catchError, map} from "rxjs/operators";
import {CatToy, Toy} from "./cat-toy.model"
@Injectable({
  providedIn: 'root'
})
export class CatsService {
  private catsUrl = 'http://localhost:8080/api/cats';
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
  getCats(): Observable<any> {
    return this.httpClient.get<Array<Cat>>(this.catsUrl).pipe(
      catchError(this.handleError<Cat[]>('getCats', []))
    );
  }

  getCat(id: number): Observable<Cat> {
    return this.getCats().pipe(
      map(cats => cats.cats.find(cat => cat.id === id))
    );
  }

  update(cat: Cat): Observable<Cat> {
    const url = `${this.catsUrl}/${cat.id}`;
    return this.httpClient.put<Cat>(url, {name: cat.name, breed: cat.breed, catYears: cat.catYears, favoriteToy: {name: cat.favoriteToyName, size: cat.favoriteToySize} as Toy} as CatToy).pipe(
      catchError(this.handleError<Cat>('updateCat'))
    );
  }

  add(cat: Cat): Observable<any> {
    return this.httpClient.post<any>(this.catsUrl, {name: cat.name, breed: cat.breed, catYears: cat.catYears, favoriteToy: {name: cat.favoriteToyName, size: cat.favoriteToySize} as Toy} as CatToy).pipe(
      catchError(this.handleError<any>('addCat'))
    );
  }

  delete(catId: number): Observable<any> {
    const url = `${this.catsUrl}/${catId}`;
    console.log(url);
    return this.httpClient.request('DELETE', url, this.httpOptions).pipe(
      catchError(this.handleError('removeCat'))
    );
  }

  getCatsWithToys(): Observable<any> {
    return this.httpClient.get<Array<Cat>>(`${this.catsUrl}/toys`).pipe(
      catchError(this.handleError<Cat[]>('getCatsWithToys', []))
    );
  }
}
