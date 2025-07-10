import { Component, inject, OnInit } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { SessionStorageService } from '../../service/session-storage.service';
import { UserDto } from '../../model/UserDto';
import { HttpClientModule } from '@angular/common/http';
import { StockService } from '../../service/stock.service';
import { StockDto } from '../../model/StockDto';
import { ROLE } from '../../constant/role.constants';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-welcome',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.scss'
})
export class WelcomeComponent implements OnInit{

  username:UserDto = new UserDto();
  sessionStorageAuth = inject(SessionStorageService);

  constructor(private router:Router)
  {
      
  }

  ngOnInit(): void {
    if(this.sessionStorageAuth.isAuthenticated())
    {
      this.username = this.sessionStorageAuth.getUser()!;
    }
  }

  isAdmin():boolean
  {
    return this.username.authoritiesList.includes(ROLE.ADMIN);
  }

  isUser():boolean
  {
    return this.username.authoritiesList.includes(ROLE.USER);
  }

   isTrack():boolean
  {
    return this.username.authoritiesList.includes(ROLE.TRACK);
  }

}
