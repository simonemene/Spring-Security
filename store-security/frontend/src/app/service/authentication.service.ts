import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { UserDto } from '../model/UserDto'

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  urlBase = environment.apiBaseUrl;

  constructor(private http:HttpClient) { }


  authentication(): Observable<UserDto>
  {
    return this.http.get<UserDto>(this.urlBase + "/user");
  }
}
