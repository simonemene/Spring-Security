import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { UserDto } from '../../model/UserDto';
import { UserService } from '../../service/user.service';
import { AuthenticationService } from '../../service/authentication.service';

@Component({
  selector: 'app-manage-profile',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './manage-profile.component.html',
  styleUrl: './manage-profile.component.scss'
})
export class ManageProfileComponent implements OnInit {

  activatedRoute = inject(ActivatedRoute);
  userService = inject(UserService);
  authUser = inject(AuthenticationService);

  username: string = '';
  userDto: UserDto = new UserDto();
  admin: boolean = false;

  profileForm!: FormGroup;


  constructor() {
    this.profileForm = new FormGroup(
      {
        username: new FormControl(this.userDto.username, [Validators.email]),
        age: new FormControl(this.userDto.age, [
          Validators.pattern("^[0-9]*$"),
          Validators.min(18),
          Validators.max(99)
        ])

      }
    )
    this.admin = this.activatedRoute.snapshot.data['admin'];
  }

  ngOnInit(): void {
    if (this.admin) {
      this.activatedRoute.params.subscribe(
        params => {
          this.username = params['username'];
          this.userService.getProfile(this.username).subscribe(
            {
              next: (userDate: UserDto) => {
                this.userDto = userDate;
              },
              error: err => console.error(err)
            }
          )
        }
      )
    } else {
      this.authUser.getUser().subscribe(
        {
          next: (user: UserDto) => {
            this.userDto = user;
          },
          error: err => console.error(err)

        }
      )
    }
  }

  onSubmit() {
      console.log(this.profileForm);
  }



}
