import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { URL } from '../constant/url.constants';
import { AllOrderDto } from '../model/AllOrderDto';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  urlBase:string = environment.apiBaseUrl;

  constructor(private http:HttpClient) { }

  getAllOrderUser(username:string)
  {
     return this.http.get<AllOrderDto>(`${this.urlBase}${URL.ALLORDER}/${username}`,{withCredentials:true});
  }


}
