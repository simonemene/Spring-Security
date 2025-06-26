import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { SessionStorageService } from '../service/session-storage.service';

export const authenticationGuard: CanActivateFn = (route, state) => {
  const autheticated = inject(SessionStorageService);
  return autheticated.isAuthenticated();
};
