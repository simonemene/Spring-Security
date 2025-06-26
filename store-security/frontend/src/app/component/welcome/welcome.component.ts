import { Component, inject, OnInit } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';

@Component({
  selector: 'app-welcome',
  standalone: true,
  imports: [],
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.scss'
})
export class WelcomeComponent implements OnInit{

  prova:string = "";
  authService = inject(AuthenticationService);

  constructor()
  {
      
  }

  ngOnInit(): void {
    this.prova = JSON.parse(window.sessionStorage.getItem('user-details')!);
  }

}
