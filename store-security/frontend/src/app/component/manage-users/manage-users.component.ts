import { Component, inject } from '@angular/core';
import { AllUserDto } from '../../model/AllUserDto';
import { UserService } from '../../service/user.service';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-manage-users',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './manage-users.component.html',
  styleUrl: './manage-users.component.scss'
})
export class ManageUsersComponent {

  router = inject(Router);

  allUser!:AllUserDto;

  constructor(private userService:UserService)
  {
    this.userService.allUser().subscribe(
      {
        next:(allUser:AllUserDto)=>
        {
          this.allUser = allUser;
        },
        error:(err)=>console.error(err)
      }
    )
  }

  openUser(username:string)
  { 
    console.log(username);
    
    this.router.navigate(['/users',username])
  }

  openOrders()
  {
     
  }




}
