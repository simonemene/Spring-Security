import { Component, inject, OnInit } from '@angular/core';
import { StockService } from '../../service/stock.service';
import { ArticleService } from '../../service/article.service';
import { StockDto } from '../../model/StockDto';

@Component({
  selector: 'app-manage-article',
  standalone: true,
  imports: [],
  templateUrl: './manage-article.component.html',
  styleUrl: './manage-article.component.scss'
})
export class ManageArticleComponent implements OnInit{

  stockService = inject(StockService);
  articleService = inject(ArticleService);

  allStock:StockDto[] = [];

  constructor()
  {}
  

  ngOnInit(): void {
    this.stockService.allArticleInStock().subscribe(
      {
        next:(stock:StockDto[])=>
        {
          this.allStock = stock;
        },
        error:(err)=>console.error(err)
      }
    )
  }



  

}
