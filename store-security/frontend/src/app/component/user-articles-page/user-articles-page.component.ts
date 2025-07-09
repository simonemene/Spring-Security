import { Component, inject } from '@angular/core';
import { StockArticleDto } from '../../model/StockArticleDto';
import { AllStockDto } from '../../model/AllStockDto';
import { ArticleService } from '../../service/article.service';
import { StockService } from '../../service/stock.service';
import { ArticlesOrderDto } from '../../model/ArticlesOrderDto';
import { MatDialog } from '@angular/material/dialog';
import { CartComponent } from '../../shared/cart/cart.component';
import { AlertComponent } from '../../shared/component/alert/alert.component';

@Component({
  selector: 'app-user-articles-page',
  standalone: true,
  imports: [AlertComponent],
  templateUrl: './user-articles-page.component.html',
  styleUrl: './user-articles-page.component.scss'
})
export class UserArticlesPageComponent {

  stockService = inject(StockService);
  articles:StockArticleDto[] = [];
  message:string = '';
  modifyError:boolean=false;
  readonly dialog = inject(MatDialog);

  order:StockArticleDto[] = [];

  constructor()
  {
    this.loadArticle();
  }


  private loadArticle()
  {
     this.stockService.allArticleInStockWithQuantity().subscribe(
      {
        next:(artilces:StockArticleDto[])=>
        {         
          for(let i=0; i< artilces.length; i++)
          {
             if(this.articles[i].quantity>0)
             {
              this.articles.push(this.articles[i]);
             }
          }
        },
        error:err=>console.error(err) 
      }
    )
  }

  add(article:StockArticleDto)
  {
    this.order.push(article);
  }

  remove(article:StockArticleDto)
  {
    let index = this.order.indexOf(article);
    if(index>=0)
    {
      this.order.splice(index,1);
    }else
    {
      this.message="Article not added";
    }
  }

  openDialog() {
    const dialogRef = this.dialog.open(CartComponent,
      {
        data:this.articles
      }
    );

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }



}

