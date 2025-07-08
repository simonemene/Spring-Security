import { Component, inject } from '@angular/core';
import { ArticleService } from '../../service/article.service';
import { ListArticleDto } from '../../model/ListArticleDto';
import { StockDto } from '../../model/StockDto';
import { StockService } from '../../service/stock.service';
import { StockArticleDto } from '../../model/StockArticleDto';
import { AllStockDto } from '../../model/AllStockDto';

@Component({
  selector: 'app-list-article',
  standalone: true,
  imports: [],
  templateUrl: './list-article.component.html',
  styleUrl: './list-article.component.scss'
})
export class ListArticleComponent {

  articleService = inject(ArticleService);
  stockService = inject(StockService);
  articles:StockArticleDto[] = [];


  constructor()
  {
    this.loadArticle();
  }

  quantity(id:number)
  {  
    this.stockService.addQuantityArticle(id).subscribe(
      {
         next:(result:AllStockDto)=>
         {
          this.loadArticle();
         }
      }
    )
  }

  minus()
  {

  }

  private loadArticle()
  {
     this.stockService.allArticleInStockWithQuantity().subscribe(
      {
        next:(artilces:StockArticleDto[])=>
        {
          console.log(artilces);
          
          this.articles = artilces;
        },
        error:err=>console.error(err) 
      }
    )
  }



}
