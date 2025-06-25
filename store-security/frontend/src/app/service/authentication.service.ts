import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  

  constructor(private http:HttpClient) { }


  authentication(): Observable<string>
  {
    return this.http.get("http://localhost:8080/prova", { responseType: 'text' });
  }
}
