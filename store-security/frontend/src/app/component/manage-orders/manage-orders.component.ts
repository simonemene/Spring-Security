import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderService } from '../../service/order.service';
import { AllOrderDto } from '../../model/AllOrderDto';

@Component({
  selector: 'app-manage-orders',
  standalone: true,
  imports: [],
  templateUrl: './manage-orders.component.html',
  styleUrl: './manage-orders.component.scss'
})
export class ManageOrdersComponent implements OnInit{

  activatedRoute = inject(ActivatedRoute);
  orderService = inject(OrderService);

  username:string = '';
  orders:AllOrderDto = new AllOrderDto();

  constructor()
  {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(param=>
      {      
        this.username = param['username'];
        this.orderService.getAllOrderUser(this.username).subscribe(
          {
            next:(allorder:AllOrderDto)=>
            {              
                this.orders = allorder; 
            },
            error:err=>console.error(err)
            
          }
        )
      }
    )
  }

}
