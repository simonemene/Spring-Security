import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AllUserDto } from '../model/AllUserDto';
import { URL } from '../constant/url.constants';
import { Observable } from 'rxjs';
import { UserDto } from '../model/UserDto';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseUrl:string = environment.apiBaseUrl;

  constructor(private http:HttpClient) { }

  allUser():Observable<AllUserDto>
  {
    return this.http.get<AllUserDto>(this.baseUrl + URL.ALLUSER,{withCredentials:true});
  }

  getProfile(username:string):Observable<UserDto>
  {
    let params = new HttpParams();
    params.append("username",username);
    return this.http.get<UserDto>(`${this.baseUrl}${URL.ALLUSER}/${username}`,{params,withCredentials:true});
  }
}
