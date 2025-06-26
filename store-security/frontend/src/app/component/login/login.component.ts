import { Component } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { UserDto } from '../../model/UserDto';
import { FormControl, FormGroup, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { Route, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  user!:UserDto;
  storeForm!:FormGroup;

  constructor(private auth:AuthenticationService,private router:Router)
  {
    this.user = new UserDto();
    this.storeForm = new FormGroup(
      {
        email: new FormControl('',[Validators.required,Validators.email]),
        password : new FormControl('',[Validators.required])
      }
    )
  }

  onSubmit()
  {
    this.user.username=this.storeForm.value.email;
    this.user.password=this.storeForm.value.password;

    this.auth.authentication(this.user).subscribe(
      responseData=>
      {
        this.user = <any> responseData.body;
        this.user.auth='AUTH';
        window.sessionStorage.setItem('user-details',JSON.stringify(this.user));
        this.router.navigate(['/welcome']);
      }
    )
  }
}
