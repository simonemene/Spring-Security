import { Component, Inject, inject, Input, OnInit } from '@angular/core';
import { StockArticleDto } from '../../model/StockArticleDto';
import { OrderService } from '../../service/order.service';
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [MatDialogModule,CommonModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent implements OnInit{

  articles: StockArticleDto[] = [];
  orderService = inject(OrderService);
  total:number = 0;

  constructor(@Inject(MAT_DIALOG_DATA) public data: StockArticleDto[], private dialogRef: MatDialogRef<CartComponent>)
  {
     this.articles = data;
  }

  ngOnInit(): void {
    this.calculatePrice();
  }

  removeItem(article:StockArticleDto)
  {
    let index = this.articles.indexOf(article);
    this.articles.splice(index,1);
    this.calculatePrice();
  }

  private calculatePrice()
  {
    for(let i=0; i<this.articles.length;i++)
    {
        this.total += this.articles[i].article.price;
    }
  }

  closeDialog()
  {
    this.dialogRef.close(this.articles);
  }

}
