import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderService } from '../../service/order.service';
import { AllOrderDto } from '../../model/AllOrderDto';
import { AlertComponent } from '../../shared/component/alert/alert.component';

@Component({
  selector: 'app-manage-orders',
  standalone: true,
  imports: [AlertComponent],
  templateUrl: './manage-orders.component.html',
  styleUrl: './manage-orders.component.scss',
})
export class ManageOrdersComponent implements OnInit {
  activatedRoute = inject(ActivatedRoute);
  orderService = inject(OrderService);

  username: string = '';
  orders: AllOrderDto = new AllOrderDto();

  modifyError: boolean = false;
  message: string = '';

  constructor() {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((param) => {
      this.username = param['username'];
      console.log(this.username);
      console.log(param['username']);
      
      
      this.orderService.getAllOrderUser(this.username).subscribe({
        next: (orders: AllOrderDto) => {
          this.orders = orders;
        },
        error: (err) => {
          console.error(err);
          this.modifyError = true;
          this.message = 'ORDER NOT FOUND';
        },
      });
    });
  }
}
