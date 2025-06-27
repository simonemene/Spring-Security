import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AllUserDto } from '../model/AllUserDto';
import { URL } from '../constant/url.constants';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseUrl:string = environment.apiBaseUrl;

  constructor(private http:HttpClient) { }

  allUser():Observable<AllUserDto>
  {
    return this.http.get<AllUserDto>(this.baseUrl + URL.ALLUSER);
  }
}
