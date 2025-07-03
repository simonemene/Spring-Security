import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { ArticleDto } from '../model/ArticleDto';
import { URL } from '../constant/url.constants';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  baseUrl:string = environment.apiBaseUrl;

  constructor(private http:HttpClient) { }

  addArticle(article:ArticleDto):Observable<ArticleDto>
  {
    console.log(article);
    
    return this.http.post<ArticleDto>(this.baseUrl + URL.ADDARTICLE,article,{withCredentials:true});
  }
}
