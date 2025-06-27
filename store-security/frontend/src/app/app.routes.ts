import { Routes } from '@angular/router';
import { HomeComponent } from './component/home/home.component';
import { WelcomeComponent } from './component/welcome/welcome.component';
import { LoginComponent } from './component/login/login.component';
import { LogoutComponent } from './component/logout/logout.component';
import { authenticationGuard } from './guard/authentication.guard';
import { roleGuard } from './guard/role.guard';
import { ROLE } from './constant/role.constants';
import { RegisterComponent } from './component/register/register.component';
import { ManageUsersComponent } from './component/manage-users/manage-users.component';

export const routes: Routes = [
    {
        path:'', component:HomeComponent, pathMatch:'full'
    },
    {
        path:'welcome',component:WelcomeComponent,
        canActivate:[authenticationGuard,roleGuard],
        data:{roles:[ROLE.USER,ROLE.ADMIN]}
    },
    {
        path:'login', component:LoginComponent
    },
    {
        path:'logout', component:LogoutComponent,
        canActivate:[authenticationGuard,roleGuard],
        data:{roles:[ROLE.USER,ROLE.ADMIN]}
    },
    {
        path:'signup', component:RegisterComponent
    },
    {
        path:'allusers',component:ManageUsersComponent,
        canActivate:[authenticationGuard,roleGuard],
        data:{roles:[ROLE.ADMIN]}
    },
    {
        path:'**', component:HomeComponent
    }
];
