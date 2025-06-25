import { Component } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { UserDto } from '../../model/UserDto';
import { FormControl, FormGroup, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';

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

  constructor(private auth:AuthenticationService)
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
    console.log(this.storeForm.value.password);
    console.log(this.storeForm.value.email);
    
    
  }


}
