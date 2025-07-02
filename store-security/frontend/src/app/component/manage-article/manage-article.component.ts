import { Component, inject, OnInit } from '@angular/core';
import { StockService } from '../../service/stock.service';
import { ArticleService } from '../../service/article.service';
import { StockDto } from '../../model/StockDto';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ArticleDto } from '../../model/ArticleDto';

@Component({
  selector: 'app-manage-article',
  standalone: true,
  imports: [FormsModule,ReactiveFormsModule],
  templateUrl: './manage-article.component.html',
  styleUrl: './manage-article.component.scss'
})
export class ManageArticleComponent implements OnInit{

  articleService = inject(ArticleService);

  articleForm!:FormGroup;
  articleDto:ArticleDto = new ArticleDto();

  constructor()
  {}
  

  ngOnInit(): void {
    this.articleForm = new FormGroup(
      {
         name: new FormControl('',Validators.required),
         description: new FormControl('',Validators.required),
         price: new FormControl(0,[Validators.max(100),Validators.min(1)])
      }
    )
  }

  onSubmit()
  {
     console.log(this.articleForm);
     this.articleDto.description=this.articleForm.value.description;
     this.articleDto.name=this.articleForm.value.name;
     this.articleDto.price=this.articleForm.value.price;
     this.articleService.addArticle(this.articleDto).subscribe(
      {
        next:(articleInsert:ArticleDto)=>
        {
          console.log(articleInsert);
        },
        error:err=>console.error(err)
      }
     )
     
  }

  



  

}
