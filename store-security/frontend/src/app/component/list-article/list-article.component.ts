import { Component, inject } from '@angular/core';
import { ArticleService } from '../../service/article.service';
import { ListArticleDto } from '../../model/ListArticleDto';

@Component({
  selector: 'app-list-article',
  standalone: true,
  imports: [],
  templateUrl: './list-article.component.html',
  styleUrl: './list-article.component.scss'
})
export class ListArticleComponent {

  articleService = inject(ArticleService);
  articles:ListArticleDto = new ListArticleDto();


  constructor()
  {
    this.articleService.getAllArticle().subscribe(
      {
        next:(artilces:ListArticleDto)=>
        {
          this.articles = artilces;
          console.log(this.articles);
          
        },
        error:err=>console.error(err) 
      }
    )
  }



}
