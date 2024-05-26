import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Cat} from "./cat.model";
import {catchError} from "rxjs/operators";
import {CatToy, Toy} from "./cat-toy.model";

@Injectable({
  providedIn: 'root'
})
export class ToyService {

  private toysUrl = 'http://localhost:8080/api/toys';
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
  getToys(): Observable<any> {
    return this.httpClient.get<Array<Toy>>(this.toysUrl).pipe(
      catchError(this.handleError<Toy[]>('getToys', []))
    );
  }

  add(toy: Toy): Observable<any> {
    return this.httpClient.post<any>(this.toysUrl, toy).pipe(
      catchError(this.handleError<any>('addToy'))
    );
  }

  assignToyToCat(toy: Toy, cat: Cat): Observable<any> {
    return this.httpClient.put<any>(`${this.toysUrl}/${cat.id}&${toy.id}`, toy)
      .pipe(catchError(this.handleError<any>('assignToyToCat')))
  }

}
