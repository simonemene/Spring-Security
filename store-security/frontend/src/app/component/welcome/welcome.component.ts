import { Component, inject, OnInit } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { SessionStorageService } from '../../service/session-storage.service';
import { UserDto } from '../../model/UserDto';
import { HttpClientModule } from '@angular/common/http';
import { StockService } from '../../service/stock.service';
import { StockDto } from '../../model/StockDto';

@Component({
  selector: 'app-welcome',
  standalone: true,
  imports: [],
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.scss'
})
export class WelcomeComponent implements OnInit{

  username:UserDto = new UserDto();
  sessionStorageAuth = inject(SessionStorageService);

  constructor()
  {
      
  }

  ngOnInit(): void {
    if(this.sessionStorageAuth.isAuthenticated())
    {
      this.username = this.sessionStorageAuth.getUser()!;
    }
  }

}
