import { Component } from '@angular/core';
import { AllUserDto } from '../../model/AllUserDto';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-manage-users',
  standalone: true,
  imports: [],
  templateUrl: './manage-users.component.html',
  styleUrl: './manage-users.component.scss'
})
export class ManageUsersComponent {

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




}
