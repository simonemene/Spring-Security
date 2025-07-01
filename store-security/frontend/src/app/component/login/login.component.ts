import { Component, inject } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { UserDto } from '../../model/UserDto';
import { FormControl, FormGroup, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { Route, Router, RouterModule } from '@angular/router';
import { SessionStorageService } from '../../service/session-storage.service';
import { getCookie } from 'typescript-cookie';

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
  errorAuthentication:boolean=false;
  sessionStorageAuth = inject(SessionStorageService);

  constructor(private auth:AuthenticationService,private router:Router)
  {
    window.sessionStorage.clear();
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
      {
        next:(responseData)=>
        {
          this.errorAuthentication=false;
          this.user = <any> responseData.body;
          let csrf = getCookie("XSRF-TOKEN")!;
          window.sessionStorage.setItem("XSRF-TOKEN",csrf);
          this.sessionStorageAuth.login(this.user);
          this.router.navigate(['/welcome']);
        },
        error:(err)=>
        {
          if(err.status === 401)
          {
            console.log("Authentication error");
            this.errorAuthentication=true;
          }
        }
      }
    )
  }
}
