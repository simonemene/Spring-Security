import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { UserDto } from '../model/UserDto'
import { URL } from '../contants/app.constants';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  urlBase = environment.apiBaseUrl;

  constructor(private http:HttpClient) { }


  authentication(user:UserDto)
  {
    window.sessionStorage.setItem("user-details",JSON.stringify(user));
    return this.http.get(this.urlBase + URL.AUTH,{observe:'response',withCredentials:true});
  }
}
