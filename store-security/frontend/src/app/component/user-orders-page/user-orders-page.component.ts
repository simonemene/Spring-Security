import { Component, inject, OnInit } from '@angular/core';
import { OrderService } from '../../service/order.service';
import { AllOrderDto } from '../../model/AllOrderDto';
import { AuthenticationService } from '../../service/authentication.service';
import { UserDto } from '../../model/UserDto';
import { AlertComponent } from '../../shared/component/alert/alert.component';

@Component({
  selector: 'app-user-orders-page',
  standalone: true,
  imports: [AlertComponent],
  templateUrl: './user-orders-page.component.html',
  styleUrl: './user-orders-page.component.scss',
})
export class UserOrdersPageComponent implements OnInit {
  orderService = inject(OrderService);
  order: AllOrderDto = new AllOrderDto();
  authService = inject(AuthenticationService);
  message: string = '';
  modifyError: boolean = false;

  constructor() {}

  ngOnInit(): void {
    this.authService.getUser().subscribe({
      next: (user: UserDto) => {
        this.orderService.getAllOrderUser(user.username).subscribe({
          next: (orders: AllOrderDto) => {
            this.order = orders;
          },
          error: (err) => {
            console.error(err);
            this.modifyError = true;
            this.message = 'ORDER NOT FOUND';
          },
        });
      },
      error: (err) => {
        console.error(err);
        this.modifyError = true;
        this.message = 'ORDER NOT FOUND';
      },
    });
  }
}
