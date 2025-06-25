import { HttpHeaders, HttpInterceptorFn } from '@angular/common/http';
import { UserDto } from '../model/UserDto';


export const httpInterceptor: HttpInterceptorFn = (req, next) => {

  let user = new UserDto();
  let httpHeaders = new HttpHeaders();

  if(window.sessionStorage.getItem('user-details'))
  {
     user = JSON.parse(window.sessionStorage.getItem('user-details')!);
  }

  if(user.username!= '' && user.password != '')
  {
     httpHeaders = httpHeaders.append('Authorization','Basic ' + window.btoa(user.username + ":" + user.password));
  }
   


  return next(req);
};
