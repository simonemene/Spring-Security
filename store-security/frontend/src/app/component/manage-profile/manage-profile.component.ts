import { Component, inject, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { UserDto } from '../../model/UserDto';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-manage-profile',
  standalone: true,
  imports: [ReactiveFormsModule,FormsModule],
  templateUrl: './manage-profile.component.html',
  styleUrl: './manage-profile.component.scss'
})
export class ManageProfileComponent implements OnInit {

  activatedRoute = inject(ActivatedRoute);
  userService = inject(UserService);

  username:string = '';
  userDto:UserDto = new UserDto();


  constructor()
  {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(
      params=>
      {
        this.username = params['username'];
        this.userService.getProfile(this.username).subscribe(
          {
            next:(userDate:UserDto)=>
            {
              this.userDto = userDate;            
            },
            error:err=> console.error(err)
          }
        )
      }
    )
  }



}
