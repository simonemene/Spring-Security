import { Routes } from '@angular/router';
import { HomeComponent } from './component/home/home.component';
import { WelcomeComponent } from './component/welcome/welcome.component';
import { LoginComponent } from './component/login/login.component';

export const routes: Routes = [
    {
        path:'', component:HomeComponent, pathMatch:'full'
    },
    {
        path:'welcome',component:WelcomeComponent
    },
    {
        path:'login', component:LoginComponent
    }
];
