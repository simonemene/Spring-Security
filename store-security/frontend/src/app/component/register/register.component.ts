import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  errorRegister:boolean = false;
  messageErrorRegister:string='';

  registerForm!:FormGroup;

  constructor()
  {
    this.registerForm = new FormGroup(
      {
        email: new FormControl('',[Validators.email,Validators.required]),
        age: new FormControl(0,[Validators.required,Validators.min(18),Validators.max(100)]),
        password: new FormControl('', [
           Validators.required,
           Validators.pattern('^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$')
        ]),
        repeatpassword: new FormControl('',[
           Validators.required
          ,Validators.pattern('^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$')
])
      }
    )
  }





  onSubmit()
  {}



}
