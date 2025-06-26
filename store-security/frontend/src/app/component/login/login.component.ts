import { Component, inject } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { UserDto } from '../../model/UserDto';
import { FormControl, FormGroup, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { Route, Router, RouterModule } from '@angular/router';
import { SessionStorageService } from '../../service/session-storage.service';

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
  sessionStorageAuth = inject(SessionStorageService);

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
        this.sessionStorageAuth.login(this.user);
        this.router.navigate(['/welcome']);
      }
    )
  }
}
