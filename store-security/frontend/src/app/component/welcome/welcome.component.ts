import { Component, inject } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';

@Component({
  selector: 'app-welcome',
  standalone: true,
  imports: [],
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.scss'
})
export class WelcomeComponent {

  prova:string = "";
  authService = inject(AuthenticationService);

  constructor()
  {
      this.authService.authentication().subscribe(
        {
          next:(prova:string)=>this.prova = prova
        ,
        error:err=>console.error(err)
        }
        
      );
  }

}
