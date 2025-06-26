import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { URL } from '../constant/url.constants';
import { map, Observable } from 'rxjs';
import { AllStockDto } from '../model/AllStockDto';
import { StockDto } from '../model/StockDto';

@Injectable({
  providedIn: 'root'
})
export class StockService {

  urlBase = environment.apiBaseUrl;

  constructor(private http:HttpClient) { }

  allArticleInStock():Observable<StockDto[]>
  {
    return this.http.get<AllStockDto>(this.urlBase + URL.ALLSTOCK,{withCredentials:true})
    .pipe(
      map(
        allStock=>
          {
            return  allStock.stock
          }
      )
    )
  }
}
