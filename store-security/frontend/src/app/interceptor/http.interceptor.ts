import { HttpErrorResponse, HttpHeaders, HttpInterceptorFn } from '@angular/common/http';
import { UserDto } from '../model/UserDto';
import { tap } from 'rxjs';
import { inject } from '@angular/core';
import { Router } from '@angular/router';


export const httpInterceptor: HttpInterceptorFn = (req, next) => {
   
   const router=inject(Router);

  let user = new UserDto();
  let httpHeaders = new HttpHeaders();

  if(window.sessionStorage.getItem('user-details'))
  {
   console.log("credenziali esistenti");
   
     user = JSON.parse(window.sessionStorage.getItem('user-details')!);
  }
  console.log(user);
  
  if(user.username && user.password)
  {
   console.log("dentro auth");
   
     httpHeaders = httpHeaders.append('Authorization','Basic ' + window.btoa(user.username + ":" + user.password));
  }

  const handleHeader = req.clone(
   {
      headers:httpHeaders
   }
  )


   return next(handleHeader).pipe(tap(
      (err:any)=>
      {
         if(err instanceof HttpErrorResponse && err.status !== 401)
         {
            router.navigate(['/error']);
         }
      }
   ));
};
